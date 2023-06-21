package basic.zKernel.job;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
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
	
	public JobStepHtmlTableWriteZZZ(JobStepControllerZZZ objController) throws ExceptionZZZ {
		super(objController);
		JobStepHtmlTableWriterNew_();
	}
	private boolean JobStepHtmlTableWriterNew_() throws ExceptionZZZ {
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
				//1. Hole die Interne Application für diesen Step.
	            //	 Dazu ist in der Ini Datei dieser STEP mit seinem Aliasnamen definiert (also NICHT die Klasse incl. Packagenamen) als ein Program.
	            //	 Die Werte dann im Program hinterlegen, also Pfad				
				IJobStepControllerZZZ objController = this.getJobStepController();
				IJobZZZ objJob = objController.getJob();
				
				IApplicationZZZ objApplication = objJob.getApplicationObject();
				IKernelZZZ objKernel = objApplication.getKernelObject();
				
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
				String sJobStepPreviousOutputParameter = "TableHeadLabel";
				IJobStepOutputZZZ objOutputStepPrevious = objJobStepWithOutput.getOutput(sJobStepPreviousOutputParameter);
				if(objOutputStepPrevious==null) {
					String sLog = "Missing JobStepPreviousOutputParameter: '" + sJobStepPreviousOutputParameter + "' from the step '" + sJobStepPrevious + "'.";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				//A) Die erstellte HashMap mit HeaderId = Label
				HashMapIndexedZZZ<Integer, TableHeadZZZ> mapIndexedTableHeadLabel = new HashMapIndexedZZZ<Integer, TableHeadZZZ>();
				mapIndexedTableHeadLabel = (HashMapIndexedZZZ<Integer, TableHeadZZZ>) objOutputStepPrevious.getHashMapIndexed();
				
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
				String sDirectoryPath = FileEasyZZZ.joinFilePathName("tryout",stemp);//sonst kommt src als erster Pfadteil
			
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
		        String sFilePathTryout = FileEasyZZZ.joinFilePathName("tryout",stemp);//sonst kommt src als erster Pfadteil
		        
		        //### Ausgabewert (hier Datei) vorbereiten, sowohl fuer die Transformation, als auch als Rueckgabewert des Steps
		        String sFileName = FileEasyZZZ.getNameWithChangedEnd(fileXslt, "html");
		        String sFilePathTotal = FileEasyZZZ.joinFilePathName(sFilePathTryout,sFileName);//sonst kommt src als erster Pfadteil
		        System.out.println("Creating new file in directory '" + sFilePathTryout + "' by KernelWriterHtmlByXsltZZZ."); 
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
