package it.fadeout.risckit.business;

import it.fadeout.risckit.data.CountryRepository;
import it.fadeout.risckit.data.EventRepository;
import it.fadeout.risckit.data.GisRepository;
import it.fadeout.risckit.data.MediaRepository;
import it.fadeout.risckit.data.SocioImpactRepository;
import it.fadeout.risckit.data.UserRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;

import org.tmatesoft.svn.core.SVNException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Header;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.TabStop.Alignment;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class PdfCreator {

	private String m_sPathProject = "";

	private String m_sSvnUser = "";

	private String m_sSvnPassword = "";

	private String m_sSvnRepository = "";

	private String m_sSvnUserDomain = "";

	public PdfCreator(String sPathProject, String sSvnUser, String sSvnPassword,String sSvnUserDomain, String sSvnRepository)
	{
		m_sPathProject = sPathProject;
		m_sSvnUser = sSvnUser;
		m_sSvnPassword = sSvnPassword;
		m_sSvnUserDomain = sSvnUserDomain;
		m_sSvnRepository = sSvnRepository;
	}

	public File CreatePdf(int iIdEvent, String sProjectPath, ServletContext serveletContext)
	{
		EventRepository oEventRepo = new EventRepository();
		MediaRepository oMediaRepo = new MediaRepository();
		GisRepository oGisRepo = new GisRepository();
		SocioImpactRepository oSocioRepo = new SocioImpactRepository();
		CountryRepository oCountryRepo = new CountryRepository();
		UserRepository oUserRepo = new UserRepository();

		Event oEvent = oEventRepo.Select(iIdEvent, Event.class);
		User oUser =  oUserRepo.Select(oEvent.getUserId(), User.class);
		List<Media> oMediaList = oMediaRepo.SelectByEvent(iIdEvent);
		Gis oGis = oGisRepo.SelectByEvent(iIdEvent);
		List<SocioImpact> oSocioList = oSocioRepo.SelectByEvent(iIdEvent);
		Country oRegion = oCountryRepo.Select(oEvent.getCountryId(), Country.class);
		Country oCountry = oCountryRepo.SelectCountryByCountryCode(oRegion.getCountryCode());
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String sFileName = oCountry.getCountryCode() + "_" + oRegion.getName() + "_" + dateFormatter.format(oEvent.getStartDate()).replace("-", "_");

		File oFile = new File(sProjectPath + "pdf/" + sFileName + ".pdf");
		
		List<Media> oGenericMedia = new ArrayList<Media>();
		Document document = null;
		OutputStream oOut = null;
		try {
			oOut = new FileOutputStream(oFile);

			document = new Document(PageSize.A4, 30, 30, 30, 30);
			PdfWriter oPdfWriter = PdfWriter.getInstance(document, oOut);
			document.open();
			document.add(Logo(oPdfWriter));
			//document.add(RisckitLogo(oPdfWriter));
			document.add(Intestazione("Event Fact Sheet"));
			document.add(createEventTable(2, oEvent));
			document.add(createCountryRegionTable(2, oCountry, oRegion));
			document.add(TitoloParagrafo("Description"));
			document.add(Descrizione(oEvent.getDescription()));
			document.add(TitoloParagrafo("Physical Data"));
			document.add(PhysicalData(2, oEvent));
			document.add(TitoloParagrafo("Impacts"));
			if (oSocioList.size() > 0)
				document.add(SocioImpactsTable(2, oSocioList));
			document.newPage();
			document.add(TitoloParagrafo("Photos"));
			DateFormat oDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
			for (Media oMedia : oMediaList) {

				Image oImage = GetImage(oMedia, oUser, oPdfWriter, serveletContext);
				if (oImage != null)
				{
					if (document.add(oImage))
					{
						document.add(FooterFoto("Figure: " + oMedia.getDescription() + " (Date: " + oDateFormatter.format(oMedia.getDate()) + ")"));
						document.newPage();
					}
					
				}
				else
					oGenericMedia.add(oMedia);
			}
				
			if (oGenericMedia.size() > 0)
			{
				document.add(TitoloParagrafo("Other supplementary media"));
				document.add(SupplementaryMediaTable(2, oGenericMedia));
			}
			if (oGis != null)
			{
				document.add(TitoloParagrafo("Supplementary GIS files"));
				document.add(GisTable(2, oGis));
			}
			//document.add(Descrizione(FOOTER));
			PdfHeaderFooter oFooter = new PdfHeaderFooter();
			oFooter.onEndPage(oPdfWriter, document);

		}
		catch(Exception oEx){
			oEx.printStackTrace();
			return null;
		}
		finally
		{
			if (document != null)
				document.close();
			if (oOut != null)
			{
				try {
					oOut.flush();
					oOut.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

		return oFile;
	}

	private Paragraph Intestazione(String sValue)
	{
		Font oFont = new Font();
		oFont.setStyle(oFont.BOLD);
		oFont.setSize(18);
		Paragraph oIntestazione = new Paragraph(sValue, oFont);
		oIntestazione.setAlignment(oIntestazione.ALIGN_CENTER);
		oIntestazione.setSpacingAfter(10);
		return oIntestazione;
	}

	private Paragraph TitoloParagrafo(String sValue)
	{
		Font oFont = new Font();
		oFont.setStyle(oFont.NORMAL);
		oFont.setSize(16);
		Paragraph oTitoloParagrafo = new Paragraph(sValue, oFont);
		oTitoloParagrafo.setAlignment(oTitoloParagrafo.ALIGN_LEFT);
		return oTitoloParagrafo;
	}

	private Paragraph Descrizione(String sValue)
	{
		Font oFont = new Font();
		oFont.setStyle(oFont.ITALIC);
		Paragraph oDescrizione = new Paragraph(sValue, oFont);
		oDescrizione.setAlignment(oDescrizione.ALIGN_JUSTIFIED);
		return oDescrizione;
	}

	private Paragraph TestoBoldUnderlineTabella(String sValue)
	{
		Font oBase = new Font();
		oBase.setStyle(Font.BOLD);
		Font oFont = new Font(oBase);
		oBase.setStyle(Font.UNDERLINE);
		Paragraph oTitoloTabella = new Paragraph(sValue, oFont);
		oTitoloTabella.setAlignment(oTitoloTabella.ALIGN_JUSTIFIED);
		return oTitoloTabella;
	}

	private Paragraph TestoBoldTabella(String sValue)
	{
		Font oBase = new Font();
		oBase.setStyle(Font.BOLD);

		Paragraph oTitoloDescrizioneTabella = new Paragraph(sValue, oBase);
		oTitoloDescrizioneTabella.setAlignment(oTitoloDescrizioneTabella.ALIGN_JUSTIFIED);
		return oTitoloDescrizioneTabella;
	}

	private Paragraph FooterFoto(String sValue)
	{
		Font oFont = new Font();
		oFont.setStyle(oFont.NORMAL);
		Paragraph oTitoloParagrafo = new Paragraph(sValue, oFont);
		oTitoloParagrafo.setAlignment(oTitoloParagrafo.ALIGN_LEFT);
		oTitoloParagrafo.setSpacingAfter(10);
		oTitoloParagrafo.setSpacingBefore(10);
		return oTitoloParagrafo;
	}

	private PdfPTable createEventTable(int iColNum, Event oEvent){
		// a table with three columns
		PdfPTable table = new PdfPTable(iColNum);
		table.setSpacingBefore(5);
		table.setSpacingAfter(5);
		// the cell object
		PdfPCell cell;
		// we add a cell with colspan 3
		cell = new PdfPCell(new Phrase("EVENT:"));
		cell.setBackgroundColor(new BaseColor(255, 204, 0));
		table.addCell(cell);
		DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		cell = new PdfPCell(new Phrase(dateFormatter.format(oEvent.getStartDate())));
		cell.setBackgroundColor(new BaseColor(255, 204, 0));
		table.addCell(cell);
		return table;
	}

	private PdfPTable createCountryRegionTable(int iColNum, Country oCountry, Country oRegion){
		// a table with three columns
		PdfPTable table = new PdfPTable(iColNum);
		table.setSpacingBefore(5);
		table.setSpacingAfter(5);
		// the cell object
		PdfPCell cell;
		//prima riga
		cell = new PdfPCell(new Phrase("Country:"));
		cell.setBackgroundColor(new BaseColor(153, 51, 0));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(oCountry.getName()));
		cell.setBackgroundColor(new BaseColor(153, 51, 0));
		table.addCell(cell);
		//nuova riga
		cell = new PdfPCell(new Phrase("Region:"));
		cell.setBackgroundColor(new BaseColor(153, 51, 0));
		cell.setBorderWidthTop(0);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(oRegion.getName()));
		cell.setBackgroundColor(new BaseColor(153, 51, 0));
		cell.setBorderWidthTop(0);
		table.addCell(cell);

		return table;
	}

	private PdfPTable PhysicalData(int iColNum, Event oEvent){
		// a table with three columns
		PdfPTable table = new PdfPTable(iColNum);
		table.setSpacingBefore(5);
		table.setSpacingAfter(5);
		// the cell object
		PdfPCell cell;
		//prima riga
		cell = new PdfPCell(TestoBoldUnderlineTabella("General Information"));
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase());
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		table.addCell(cell);
		//start date
		cell = new PdfPCell(TestoBoldTabella("Start Date:"));
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		cell.setBorderWidthTop(0);
		table.addCell(cell);
		DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		cell = new PdfPCell(new Phrase(dateFormatter.format(oEvent.getStartDate())));
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		cell.setBorderWidthTop(0);
		table.addCell(cell);
		//start hour
		cell = new PdfPCell(TestoBoldTabella("Start hour:"));
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		cell.setBorderWidthTop(0);
		table.addCell(cell);
		String sHour = "";
		DateFormat hourFormatter = new SimpleDateFormat("hh:mm");
		if (oEvent.getStartHour() != null)
			sHour = hourFormatter.format(oEvent.getStartHour());
		cell = new PdfPCell(new Phrase(sHour));
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		cell.setBorderWidthTop(0);
		table.addCell(cell);
		//Duration
		String sUnit = "";
		String sUnitValue = "";
		if (oEvent.getUnitValue() != null)
		{
			DecimalFormat df = new DecimalFormat("#.#");
			sUnitValue = df.format(oEvent.getUnitValue()).toString();
			
			if (oEvent.getUnitHour() == null || !oEvent.getUnitHour())
				sUnit = "day(s)";
			else
				sUnit = "hour(s)";
		}
		cell = new PdfPCell(TestoBoldTabella("Duration:"));
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		cell.setBorderWidthTop(0);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(sUnitValue + " " + sUnit));
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		cell.setBorderWidthTop(0);
		table.addCell(cell);
		//Wave information
		cell = new PdfPCell(TestoBoldUnderlineTabella("Wave Information"));
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase());
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		table.addCell(cell);
		//Height
		String sWaveHeightValue = "";
		String sWaveHeightType = "";
		if (oEvent.getWaveHeightType() != null)
		{
			if (oEvent.getWaveHeightType() == 1)
				sWaveHeightType = "(Mean significant wave height)";
			else if (oEvent.getWaveHeightType() == 2)
				sWaveHeightType = "(Peak significant wave height)";
			else if (oEvent.getWaveHeightType() == 3)
				sWaveHeightType = "(Maximum wave height)";
		}
		if (oEvent.getWaveHeightValue() != null)
			sWaveHeightValue = oEvent.getWaveHeightValue().toString() + " (meters)";
		cell = new PdfPCell(TestoBoldTabella("Wave height:"));
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		cell.setBorderWidthTop(0);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(sWaveHeightValue + " " + sWaveHeightType));
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		cell.setBorderWidthTop(0);
		table.addCell(cell);
		//direction
		String sWaveDirectionType = "";
		String sWaveDirectionValue = "";
		if (oEvent.getWaveDirectionType() != null)
		{
			if (oEvent.getWaveDirectionType() == 1)
			{
				sWaveDirectionType = "Degrees from N";
				if (oEvent.getWaveDirectionDegree() != null)
					sWaveDirectionValue = oEvent.getWaveDirectionDegree().toString();
			}
			else if (oEvent.getWaveDirectionType() == 2)
			{
				sWaveDirectionType = "Compass";
				if (oEvent.getWaveDirectionClustered() != null)
					sWaveDirectionValue = oEvent.getWaveDirectionClustered().toString();
			}
		}
		cell = new PdfPCell(TestoBoldTabella("Wave direction:"));
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		cell.setBorderWidthTop(0);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(sWaveDirectionValue + " " + sWaveDirectionType));
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		cell.setBorderWidthTop(0);
		table.addCell(cell);
		//Wind information
		cell = new PdfPCell(TestoBoldUnderlineTabella("Wind Information"));
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase());
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		table.addCell(cell);
		//Intensity
		String sWindIntensityType = "";
		String sWindIntensityValue = "";
		if (oEvent.getWindIntensityType() != null)
		{
			if (oEvent.getWindIntensityType() == 1)
			{
				sWindIntensityType = "(Mean wind speed)";
			}
			else if (oEvent.getWindIntensityType() == 2)
			{
				sWindIntensityType = "(Maximum wind speed)";
			}
			else if (oEvent.getWindIntensityType() == 3)
				sWindIntensityType = "(Maximum wind gust)";
		}
		cell = new PdfPCell(TestoBoldTabella("Wind intensity:"));
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		cell.setBorderWidthTop(0);
		table.addCell(cell);
		if (oEvent.getWindIntensityValue() != null)
			sWindIntensityValue = oEvent.getWindIntensityValue().toString() + " (meters/sec)";
		cell = new PdfPCell(new Phrase(sWindIntensityValue + " " + sWindIntensityType));
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		cell.setBorderWidthTop(0);
		table.addCell(cell);
		//direction
		String sWindDirectionType = "";
		String sWindDirectionValue = "";
		if (oEvent.getWaveDirectionType() != null)
		{
			if (oEvent.getWaveDirectionType() == 1)
			{
				sWaveDirectionType = "Degrees from N";
				if (oEvent.getWaveDirectionDegree() != null)
					sWaveDirectionValue = oEvent.getWaveDirectionDegree().toString();
			}
			else if (oEvent.getWaveDirectionType() == 2)
			{
				sWaveDirectionType = "Compass";
				if (oEvent.getWaveDirectionClustered() != null)
					sWaveDirectionValue = oEvent.getWaveDirectionClustered().toString();
			}
		}
		cell = new PdfPCell(TestoBoldTabella("Wind direction:"));
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		cell.setBorderWidthTop(0);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(sWindDirectionValue + " " + sWindDirectionType));
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		cell.setBorderWidthTop(0);
		table.addCell(cell);
		//Water information
		cell = new PdfPCell(TestoBoldUnderlineTabella("Water Information"));
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase());
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		table.addCell(cell);
		//value
		String sWaterLevelType = "";
		String sWaterLevelValue = "";
		if (oEvent.getWaterLevelType() != null)
		{
			if (oEvent.getWaterLevelType() == 1)
			{
				sWaterLevelType = "(Maximum total water level)";
			}
			else if (oEvent.getWaterLevelType() == 2)
			{
				sWaterLevelType = "(Maximum astronomical tide)";
			}
		}

		if (oEvent.getWaterLevelValue() != null)
			sWaterLevelValue = oEvent.getWaterLevelValue().toString() + " (meters)";
		cell = new PdfPCell(TestoBoldTabella("Value:"));
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		cell.setBorderWidthTop(0);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(sWaterLevelValue + " " + sWaterLevelType));
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		cell.setBorderWidthTop(0);
		table.addCell(cell);
		//River flooding
		String sPeakWaterDischarge = "Not relevant";
		String sFloodHeight = "Not relevant";
		if (oEvent.getPeakWaterDischarge() != null)
			sPeakWaterDischarge = oEvent.getPeakWaterDischarge().toString();
		if (oEvent.getFloodHeight() != null)
			sFloodHeight = oEvent.getFloodHeight().toString();
		cell = new PdfPCell(TestoBoldTabella("Peak water discharge:"));
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		cell.setBorderWidthTop(0);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(sPeakWaterDischarge));
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		cell.setBorderWidthTop(0);
		table.addCell(cell);
		cell = new PdfPCell(TestoBoldTabella("Flood height:"));
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		cell.setBorderWidthTop(0);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(sFloodHeight));
		cell.setBackgroundColor(new BaseColor(198, 217, 241));
		cell.setBorderWidthTop(0);
		table.addCell(cell);
		return table;
	}


	private PdfPTable SocioImpactsTable(int iColNum, List<SocioImpact> oList){
		// a table with three columns
		PdfPTable table = new PdfPTable(iColNum);
		table.setSpacingBefore(5);
		table.setSpacingAfter(5);
		// the cell object
		PdfPCell cell;
		// we add a cell with colspan 3
		Float fTotalCost = (float) 0;
		DecimalFormat totalCostFormatter = new DecimalFormat("#");
		String sCurrency = "";
		for (SocioImpact socioImpact : oList) {
			cell = new PdfPCell(TestoBoldTabella("Category:"));
			cell.setBackgroundColor(new BaseColor(253, 233, 217));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase(socioImpact.getSubCategory().getCategory().getDescription()));
			cell.setBackgroundColor(new BaseColor(253, 233, 217));
			table.addCell(cell);
			cell = new PdfPCell(TestoBoldTabella("Sub category:"));
			cell.setBackgroundColor(new BaseColor(253, 233, 217));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase(socioImpact.getSubCategory().getDescription()));
			cell.setBackgroundColor(new BaseColor(253, 233, 217));
			table.addCell(cell);
			cell = new PdfPCell(TestoBoldTabella("Description:"));
			cell.setBackgroundColor(new BaseColor(253, 233, 217));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase(socioImpact.getDescription()));
			cell.setBackgroundColor(new BaseColor(253, 233, 217));
			table.addCell(cell);
			cell = new PdfPCell(TestoBoldTabella("Quantitative description:"));
			cell.setBackgroundColor(new BaseColor(253, 233, 217));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase(socioImpact.getUnitMeasure()));
			cell.setBackgroundColor(new BaseColor(253, 233, 217));
			table.addCell(cell);
			cell = new PdfPCell(TestoBoldTabella("Cost:"));
			cell.setBackgroundColor(new BaseColor(253, 233, 217));
			table.addCell(cell);
			if (socioImpact.getCurrency() != null)
				sCurrency = socioImpact.getCurrency().getCurrency();
			float fCost=0;
			if (socioImpact.getCost() != null)
				fCost = socioImpact.getCost().floatValue();
			String sCost = "";
			if (fCost > 0)
				sCost = totalCostFormatter.format(fCost);
			cell = new PdfPCell(new Phrase(sCost + " " + sCurrency));
			cell.setBackgroundColor(new BaseColor(253, 233, 217));
			table.addCell(cell);
			fTotalCost += fCost;
		}

		if (oList.size() > 0)
		{
			cell = new PdfPCell(TestoBoldUnderlineTabella("Total Cost:"));
			cell.setBackgroundColor(new BaseColor(253, 233, 217));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase(totalCostFormatter.format(fTotalCost) + " " + sCurrency));
			cell.setBackgroundColor(new BaseColor(253, 233, 217));
			table.addCell(cell);
		}

		return table;
	}

	private PdfPTable SupplementaryMediaTable(int iColNum, List<Media> oList){
		// a table with three columns
		PdfPTable table = new PdfPTable(iColNum);
		table.setSpacingBefore(5);
		table.setSpacingAfter(5);
		// the cell object
		PdfPCell cell;
		// we add a cell with colspan 3
		for (Media oMedia : oList) {
			cell = new PdfPCell(TestoBoldTabella("File name:"));
			cell.setBackgroundColor(new BaseColor(234, 241, 221));
			table.addCell(cell);
			String sRepoFile = oMedia.getFile();
			String[] sSplitString = sRepoFile.split("/");
			final String sTemp = sSplitString[sSplitString.length - 1];
			cell = new PdfPCell(new Phrase(sRepoFile));
			cell.setBackgroundColor(new BaseColor(234, 241, 221));
			table.addCell(cell);
			cell = new PdfPCell(TestoBoldTabella("Description:"));
			cell.setBackgroundColor(new BaseColor(234, 241, 221));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase(oMedia.getDescription()));
			cell.setBackgroundColor(new BaseColor(234, 241, 221));
			table.addCell(cell);
			cell = new PdfPCell(TestoBoldTabella("Date:"));
			cell.setBackgroundColor(new BaseColor(234, 241, 221));
			table.addCell(cell);
			DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
			cell = new PdfPCell(new Phrase(dateFormatter.format(oMedia.getDate())));
			cell.setBackgroundColor(new BaseColor(234, 241, 221));
			table.addCell(cell);

		}
		return table;
	}


	private PdfPTable GisTable(int iColNum, Gis oGis){
		// a table with three columns
		PdfPTable table = new PdfPTable(iColNum);
		table.setSpacingBefore(5);
		table.setSpacingAfter(5);
		// the cell object
		PdfPCell cell;
		// we add a cell with colspan 3

		cell = new PdfPCell(TestoBoldTabella("File name:"));
		cell.setBackgroundColor(new BaseColor(234, 241, 221));
		table.addCell(cell);
		String sRepoFile = "";
		if (oGis != null)
		{
			if (oGis.getGisFile() != null)
				sRepoFile = oGis.getGisFile();
		}
		String[] sSplitString = sRepoFile.split("/");
		final String sTemp = sSplitString[sSplitString.length - 1];
		cell = new PdfPCell(new Phrase(sRepoFile));
		cell.setBackgroundColor(new BaseColor(234, 241, 221));
		table.addCell(cell);
		cell = new PdfPCell(TestoBoldTabella("Description:"));
		cell.setBackgroundColor(new BaseColor(234, 241, 221));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("Areas flooded by the storm"));
		cell.setBackgroundColor(new BaseColor(234, 241, 221));
		table.addCell(cell);

		return table;
	}


	private Image GetImage(Media oMedia, User oUser, PdfWriter oWriter, ServletContext oContext) throws IOException, DocumentException
	{
		String sRepoFile = oMedia.getFile();
		String[] sSplitString = sRepoFile.split("/");
		final String sTemp = sSplitString[sSplitString.length - 1];

		SVNUtils oSvnUtils = new SVNUtils();
		File oFile = new File(m_sPathProject + "img/thumb/temp/" + sTemp);
		OutputStream oOut = null;

		if (!oFile.exists())
		{
			try
			{
				oOut = new FileOutputStream(oFile);
				oSvnUtils.GetFile(
						oUser.getUserName(),
						m_sSvnUser, 
						m_sSvnPassword, 
						m_sSvnUserDomain, 
						oMedia.getFile(), 
						m_sSvnRepository,
						oOut);

			}
			catch (SVNException oEx)
			{
				oEx.printStackTrace();
				oOut.flush();
				oOut.close();

				return null;
			}
		}

		//Ritorno il path solo se è un'immagine
		String mimetype= oContext.getMimeType(oFile.getName());
		if (mimetype != null)
		{
			if (mimetype.startsWith("image"))
			{
				Image img = null;
				try
				{
					img = Image.getInstance(m_sPathProject + "img/thumb/temp/" + sTemp);
				}
				catch(Exception oEx)
				{
					oEx.printStackTrace();
					return null;
				}
				if (img != null)
				{
					float w = img.getScaledWidth();
					float h = img.getScaledHeight();
					float fAspectRatio = w/h;
					
					PdfTemplate t;
					
					if (w > PageSize.A4.getWidth() - 100)
					{
						w = PageSize.A4.getWidth() - 100;
						h = w / fAspectRatio;
					}
					
					t = oWriter.getDirectContent().createTemplate(w, h);
					t.addImage(img, w, 0, 0, h, 0, 0, false);
					
					Image clipped = Image.getInstance(t);
					
					return clipped;
				}
			}
		}

		return null;
	}

	private Image Logo(PdfWriter oWriter) throws MalformedURLException, IOException, DocumentException
	{
		Image imgeurope = Image.getInstance(m_sPathProject + "/img/europe.jpg");
		Image imglogo = Image.getInstance(m_sPathProject + "/img/pdf_risck-it.jpg");
		PdfTemplate t = oWriter.getDirectContent().createTemplate(PageSize.A4.getWidth() *2 , 120);
		float w = imgeurope.getScaledWidth();
		float h = imgeurope.getScaledHeight();
		t.addImage(imgeurope, w, 0, 0, h, 0, 0);
		w = imglogo.getScaledWidth();
		h = imglogo.getScaledHeight();
		t.addImage(imglogo, w, 0, 0, h, PageSize.A4.getWidth(), 0);
		Image clipped = Image.getInstance(t);

		clipped.scalePercent(50);
		return clipped;
	}

	/*
	private Image RisckitLogo(PdfWriter oWriter) throws MalformedURLException, IOException, DocumentException
	{
		Image img = Image.getInstance(m_sPathProject + "/img/pdf_risck-it.jpg");
		float w = img.getScaledWidth();
		float h = img.getScaledHeight();
		PdfTemplate t = oWriter.getDirectContent().createTemplate(394, 125);
		t.addImage(img, w, 0, 0, h, 200, 100);
		Image clipped = Image.getInstance(t);
		clipped.scalePercent(50);
		return clipped;
	}
	 */

}
