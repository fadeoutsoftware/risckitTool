package it.fadeout.risckit;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.media.multipart.MultiPartFeature;

public class Risckit extends Application{

	@Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        // register resources and features
        classes.add(MultiPartFeature.class);
        classes.add(CountryResource.class);
        classes.add(EventResource.class);
        classes.add(MediaResource.class);
        classes.add(UserResource.class);
        classes.add(SocioImpactResource.class);
        classes.add(CategoryResource.class);
        return classes;
	}
}
