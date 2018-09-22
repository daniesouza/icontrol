package br.com.icontrol.annotations;

import java.awt.Frame;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;


public class Teste {

    public static void main(String[] args) throws ClassNotFoundException {

//    	Reflections reflections = new Reflections("br.com.icontrol");
//    	Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Criptografado.class);
//
//    	for (Class<?> controller : annotated) {
//    	    controller.getDeclaredFields();
//    	}
//    	
    	
        AnnotationRunner runner = new AnnotationRunner();
       
        Field[] methods = runner.getClass().getDeclaredFields();

        for (Field method : methods) {
        	
        	Annotation[] annotations = method.getDeclaredAnnotations();
        	
        	
        	for(Annotation annotation : annotations){
        	    if(annotation instanceof Criptografado){
        	    	Criptografado myAnnotation = (Criptografado) annotation;
        	        System.out.println("name: " + myAnnotation.name());
        	        System.out.println("value: " + myAnnotation.value());
        	        
                    try {
                    	System.out.println(method.get(runner));
                    	
                    	method.set(runner,"DEU CERTO");
                    	
                    	System.out.println(method.get(runner));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
        	    }
        	}

        }
        
        new Teste().loadClass();
    }
    
    
    private void loadClass(){  
    	
    	@SuppressWarnings("rawtypes")
		SearchClass<Class> sf  = new SearchClass<Class>();  
          
        sf.setCheck( new CheckSearch() {  
  
                public boolean checkClass(Class<?> classe) {  
                      
                    if( classe.isAnnotationPresent( Criptografado.class ))  
                        return true;  
                    else  
                        return false;  
                         
                }  
            }   
          
        );  
          
        sf.load( Thread.currentThread().getContextClassLoader() );  
        sf.loadDirs( Thread.currentThread().getContextClassLoader() );  
        
        List<Class<Class>> clazzs = sf.getClazzs();  
} 
}
