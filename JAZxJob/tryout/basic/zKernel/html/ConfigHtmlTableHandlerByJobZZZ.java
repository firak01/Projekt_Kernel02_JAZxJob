package basic.zKernel.html;

import static java.lang.System.out;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.AbstractKernelConfigZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import custom.zKernel.ConfigFGL;
import custom.zKernel.file.ini.FileIniZZZ;

public class ConfigHtmlTableHandlerByJobZZZ  extends AbstractKernelConfigZZZ{
	
	//#################################################
	//Merke: Die Konstanten sind meist nicht final, damit sie von der konkreten Konfiguration
	//       ueberschrieben werden koennen.
	//       Final sind die fuer den Kernel selbst wichtige Konstanten
	
	
	//#####################################################################
	//####### Reflektion zum Gesamtprojekt
	static String sPROJECT_DIRECTORY = "Projekt_Kernel02_JAZxJob";
	static String sPROJECT_NAME = "JAZxJob";
	
	public ConfigHtmlTableHandlerByJobZZZ() throws ExceptionZZZ{
		super();
	}
	public ConfigHtmlTableHandlerByJobZZZ(String[] saArg) throws ExceptionZZZ {
		super(saArg); 
	} 
	public ConfigHtmlTableHandlerByJobZZZ(String[] saArg, String[] saFlagControl) throws ExceptionZZZ {
		super(saArg, saFlagControl); 
	} 
	public ConfigHtmlTableHandlerByJobZZZ(String[] saArg, String sFlagControl) throws ExceptionZZZ {
		super(saArg, sFlagControl); 
	}
	
	
	public String getApplicationKeyDefault() {
		return "HtmlTableHandlerByJob"; 
	}
	
	public String getSystemNumberDefault() {
		return "02";
	}

	public String getConfigDirectoryNameDefault() {
		return "tryout";
	}
	
	public String getConfigFileNameDefault() {
		return "ZKernelConfig_HtmlTableHandlerByJob.ini";
	}

	@Override
	public String getProjectName() {
		return ConfigHtmlTableHandlerByJobZZZ.sPROJECT_NAME;
	}
	@Override
	public String getProjectDirectory() {
		return ConfigHtmlTableHandlerByJobZZZ.sPROJECT_PATH;
	}
	
}