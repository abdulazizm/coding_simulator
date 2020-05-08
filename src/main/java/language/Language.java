package language;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.shape.Path;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class Language{
	 static ObjectMapper mapper;
	 public static String keywordString,baseCodeString;
	 public static String keywords[];

	 public static void importLanguageSpecs(){
		  String fileString= null;
		  try{
			   ObjectMapper mapper=new ObjectMapper();
			   fileString = Files.readString(Paths.get("src/main/resources/language-spec.json"));
			   Map<String,String> map = mapper.readValue(fileString, Map.class);
			   keywordString=map.get("keywords");
			   baseCodeString=map.get("base_code");
			   keywords=keywordString.split(",");
		  } catch (IOException e){
			   e.printStackTrace();
		  }
	 }



}
