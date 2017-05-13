package com.java.processor.processor;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("com.java.processor.processor.BuilderAnnotation")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class BuilderAnnotationProcessor extends AbstractProcessor {


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {



        // for each javax.lang.model.element.Element annotated with the CustomAnnotation
        for (Element element : roundEnv.getElementsAnnotatedWith(BuilderAnnotation.class)) {
            StringBuilder builder = new StringBuilder();

            String className = "";
            String packageName = "abc";

                    String totalName = element.getEnclosingElement().toString();
                    int lastDot = totalName.lastIndexOf('.');
                    if (lastDot > 0) {
                        packageName = totalName.substring(0, lastDot);
                        className = totalName.substring(lastDot+1);
                    }
                    String classForInstance = className;
                    className += "Builder";
            builder.append("package "+packageName + " ;\n");
                    builder.append("\npublic class "+className + "{\n\n\t");
                    builder.append(classForInstance + " instance ;\n\t");
                    builder.append("public "+className + "(){\n\t\t");
                    builder.append(" instance = new " + classForInstance + "();\n");
                    builder.append("}\n");
                    builder.append("\tpublic " + className +" set" + element.getSimpleName() + "("+ element.asType().toString() +" x){\n");
                    builder.append("instance." + element.getSimpleName() + " = x;\n");
                    builder.append("\t\t return this;");
                    builder.append("\n}\n");
                    builder.append("public "+ classForInstance + " build(){\n");
                    builder.append("return instance;\n");
                    builder.append("}\n");

            builder.append("}");
            try { // write the file
                JavaFileObject source = processingEnv.getFiler().createSourceFile(packageName +  "."+className);


                Writer writer = source.openWriter();
                writer.write(builder.toString());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                // Note: calling e.printStackTrace() will print IO errors
                // that occur from the file already existing after its first run, this is normal
            }
        }





        return true;
    }
}
