package it.fadeout.risckit.viewmodels;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HttpRequestResult extends PrimitiveResult 
{
	public HttpRequestResult()
	{
		super();
	}
	
	public void setSuccess()
	{
		this.BoolValue = true;
	}
	
	public void setError(String sReason)
	{
		this.BoolValue = false;
		this.StringValue = sReason;
	}
	
	public boolean hasError() 
	{
		return (this.BoolValue == false);
	}
}
