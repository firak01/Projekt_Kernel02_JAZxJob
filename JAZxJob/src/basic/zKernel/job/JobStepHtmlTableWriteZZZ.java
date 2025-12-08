package basic.zKernel.job;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedObjectZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelConfigZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.net.client.IApplicationZZZ;
import basic.zKernel.net.client.IMainZZZ;
import custom.zKernel.LogZZZ;
import use.zKernel.html.step01.writer.KernelWriterHtmlByXsltZZZ;
import use.zKernel.html.step01.writer.TableHeadZZZ;
import use.zKernel.html.step02.reader.Debug02_ReaderHtmlTableZZZ;

public class JobStepHtmlTableWriteZZZ extends AbstractJobStepWithOutputZZZ {
	public static String sJOBSTEP_ALIAS="HtmlTableWriterStep";
	
	public JobStepHtmlTableWriteZZZ() throws ExceptionZZZ {
		super();
	}
	
	/**Ein JobStep, der im gleichen Modul liegt, also das gleiche Kernel-Objekt wie Application nutzt
	 * @param objController
	 * @throws ExceptionZZZ
	 * 23.06.2023, 09:44:32, Fritz Lindhauer
	 */
	public JobStepHtmlTableWriteZZZ(IJobStepControllerZZZ objController) throws ExceptionZZZ {
		super(objController);
		JobStepHtmlTableWriterNew_(null);
	}
	
	/**Ein JobStep, der in einem anderen Modul liegt, also NICHT das gleiche Kernel-Objekt wie Application nutzt
	 * @param objController
	 * @throws ExceptionZZZ
	 * 23.06.2023, 09:44:32, Fritz Lindhauer
	 */
	public JobStepHtmlTableWriteZZZ(IKernelZZZ objKernel, IJobStepControllerZZZ objController) throws ExceptionZZZ {
		super(objController);
		JobStepHtmlTableWriterNew_(objKernel);
	}
	
	private boolean JobStepHtmlTableWriterNew_(IKernelZZZ objKernel) throws ExceptionZZZ {
		if(objKernel!=null) {
			this.setKernelObject(objKernel);
		}
		return true;
	}
		
	@Override
	public String getJobStepAliasCustom() throws ExceptionZZZ {
		return sJOBSTEP_ALIAS;
	}
	
	@Override
	public boolean process() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			String stemp;String sPropertyUsed;
			try {				 
				//Da noch ein anderer Step vorgeschaltet wird,
				//wird das benötigt, um den Output des vorherigen Steps abzufragen.
				IJobStepControllerZZZ objController = this.getJobStepController();
				//IJobZZZ objJob = objController.getJob();
				//IApplicationZZZ objApplication = objJob.getApplicationObject();
				
				//1. Hole das Kernel-Objekt für diesen Step.
	            //	 Dazu ist in der Ini Datei dieser STEP mit seinem Aliasnamen definiert (also NICHT die Klasse incl. Packagenamen) als ein Program.
	            //	 Die Werte dann im Program hinterlegen, also Pfad	
				IKernelZZZ objKernel = this.getKernelObject();
				String sAlias = this.getJobStepAlias();
	 
				//###############################################
				//Ausgabeparameter des vorherigen TableColumn-Steps holen
				String sJobStepPrevious = "HtmlTableColumnHeaderStep";
				IJobStepOutputProviderZZZ objJobStepWithOutput = objController.getJobStepForOutput(sJobStepPrevious);//TODOGOON20230619 enum der JobSteps
				if(objJobStepWithOutput==null) {
					String sLog = "Missing previous JobStep with output: '" + sJobStepPrevious + "' for this step '" + this.getJobStepAlias() + "'.";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				String sJobStepPreviousOutputParameter = "TableHeadLabelMap";
				IJobStepOutputZZZ objOutputStepPrevious = objJobStepWithOutput.getOutput(sJobStepPreviousOutputParameter);
				if(objOutputStepPrevious==null) {
					String sLog = "Missing JobStepPreviousOutputParameter: '" + sJobStepPreviousOutputParameter + "' from the step '" + sJobStepPrevious + "'.";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				//A) Die erstellte HashMap mit HeaderId = Label
				HashMapIndexedObjectZZZ<Integer, TableHeadZZZ> mapIndexedTableHeadLabel = new HashMapIndexedObjectZZZ<Integer, TableHeadZZZ>();
				mapIndexedTableHeadLabel = (HashMapIndexedObjectZZZ<Integer, TableHeadZZZ>) objOutputStepPrevious.getHashMapIndexed();
												
				//##############################################
				//Werte fuer diesen Step holen
				
				//A) XML Datei fuer die Werte								
				sPropertyUsed  = "InputDirectoryPath";
				IKernelConfigSectionEntryZZZ objEntryPath = objKernel.getParameterByProgramAlias(sAlias, sPropertyUsed, true);
				if(!objEntryPath.hasAnyValue()) {
					String sLog = "Missing parameter: '" + sPropertyUsed + "' for Program '" + sAlias + "' in file '" + objKernel.getFileConfigKernelIni().getFileObject().getAbsolutePath() + "'.";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				stemp = objEntryPath.getValue();
					
				//Hier den INI-Datei Pfad verwenden. Daraus das Parent-Objekt holen und dann join des Pfads mit dem html namen/pfad
				String sDirectoryPath = this.getKernelObject().getFileConfigKernelDirectory();
				sDirectoryPath = FileEasyZZZ.joinFilePathName(sDirectoryPath,stemp);
			
				sPropertyUsed = "InputFileNameXml";
				IKernelConfigSectionEntryZZZ objEntryFileXml = objKernel.getParameterByProgramAlias(sAlias, sPropertyUsed, true);
				if(!objEntryFileXml.hasAnyValue()) {
					String sLog = "Missing parameter: '" + sPropertyUsed + "' for Program '" + sAlias + "' in file '" + objKernel.getFileConfigKernelIni().getFileObject().getAbsolutePath() + "'.";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				String sFileNameInputXml = objEntryFileXml.getValue();						
				File fileXml = FileEasyZZZ.searchFile(sDirectoryPath,sFileNameInputXml);
				if(!FileEasyZZZ.exists(fileXml)) {
					//Suche nach einer Datei im workspace, also NICHT per Classloader
					IKernelConfigZZZ objConfig = this.getKernelObject().getConfigObject();
					fileXml = FileEasyZZZ.searchFileInWorkspace(objConfig, sDirectoryPath,sFileNameInputXml);
				}
				
				//B) XSLT Datei fuer die Transformation. Diese enthaelt auch die HTML-Tags				
				sPropertyUsed = "InputFileNameXsl";
				IKernelConfigSectionEntryZZZ objEntryFileXsl = objKernel.getParameterByProgramAlias(sAlias, sPropertyUsed, true);
				if(!objEntryFileXsl.hasAnyValue()) {
					String sLog = "Missing parameter: '" + sPropertyUsed + "' for Program '" + sAlias + "' in file '" + objKernel.getFileConfigKernelIni().getFileObject().getAbsolutePath() + "'.";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				String sFileNameInputXsl = objEntryFileXsl.getValue();
				File fileXslt = FileEasyZZZ.searchFile(sDirectoryPath,sFileNameInputXsl);				
				if(!FileEasyZZZ.exists(fileXslt)) {
					//Suche nach einer Datei im workspace, also NICHT per Classloader
					IKernelConfigZZZ objConfig = this.getKernelObject().getConfigObject();
					fileXslt = FileEasyZZZ.searchFileInWorkspace(objConfig,sDirectoryPath,sFileNameInputXsl);
				}		
				
				//C) Dateipfad fuer das Ergebnis Html
				sPropertyUsed = "OutputDirectoryPath";
				IKernelConfigSectionEntryZZZ objEntryDirectoryHtml = objKernel.getParameterByProgramAlias(sAlias, sPropertyUsed, true);
				if(!objEntryDirectoryHtml.hasAnyValue()) {
					String sLog = "Missing parameter: '" + sPropertyUsed + "' for Program '" + sAlias + "' in file '" + objKernel.getFileConfigKernelIni().getFileObject().getAbsolutePath() + "'.";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				stemp = objEntryDirectoryHtml.getValue();
				sDirectoryPath = this.getKernelObject().getFileConfigKernelDirectory();
				sDirectoryPath = FileEasyZZZ.joinFilePathName(sDirectoryPath,stemp);
				
				//### Ausgabewert (hier Datei) vorbereiten, sowohl fuer die Transformation, als auch als Rueckgabewert des Steps
		        String sFileName = FileEasyZZZ.getNameWithChangedEnd(fileXslt, "html");
		        										       
	        					
		        String sFilePathTotal = FileEasyZZZ.joinFilePathName(sDirectoryPath,sFileName);//sonst kommt src als erster Pfadteil
		        System.out.println("Creating new file in directory '" + sFilePathTotal + "' by KernelWriterHtmlByXsltZZZ."); 
		        System.out.println("The new file will have the same name as the xslt file with the ending html: '" + sFilePathTotal + "'");
		        File fileHtmlOutput = new File(sFilePathTotal);

				//### Transformation durchfuehren
		        KernelWriterHtmlByXsltZZZ objWriter = new KernelWriterHtmlByXsltZZZ();
		        objWriter.setFileHtmlOutput(fileHtmlOutput);
		        objWriter.setHashMapIndexedTableHeader(mapIndexedTableHeadLabel);
				
				boolean bSuccess = objWriter.transformFileOnStyle(fileXslt, fileXml);
				if(bSuccess) {
					System.out.println("The new file should be here: '" + fileHtmlOutput + "'");
					
					//Output anderen Folgesteps zur Verfügung stellen
					IJobStepOutputZZZ objOutput = new JobStepOutputZZZ(fileHtmlOutput);
					this.addOutput("HtmlFile", objOutput);
					bReturn = true;
				}else {
					System.out.println("A problem occured during transformation.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}									
		}//end main:
		return bReturn;
	}
}
