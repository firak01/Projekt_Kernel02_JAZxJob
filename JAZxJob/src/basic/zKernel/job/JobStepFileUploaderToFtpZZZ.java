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

public class JobStepFileUploaderToFtpZZZ extends AbstractJobStepZZZ {
	public static String sJOBSTEP_ALIAS="PageUploaderToFtpStep";
	
	public JobStepFileUploaderToFtpZZZ() throws ExceptionZZZ {
		super();
	}
	
	
	/**Ein JobStep, der im gleichen Modul liegt, also das gleiche Kernel-Objekt wie Application nutzt
	 * @param objController
	 * @throws ExceptionZZZ
	 * 23.06.2023, 09:44:32, Fritz Lindhauer
	 */
	public JobStepFileUploaderToFtpZZZ(IJobStepControllerZZZ objController) throws ExceptionZZZ {
		super(objController);
		JobStepPageUploaderToFtpZZZ_(null);
	}
	
	/**Ein JobStep, der in einem anderen Modul liegt, also NICHT das gleiche Kernel-Objekt wie Application nutzt
	 * @param objController
	 * @throws ExceptionZZZ
	 * 23.06.2023, 09:44:32, Fritz Lindhauer
	 */
	public JobStepFileUploaderToFtpZZZ(IKernelZZZ objKernel, IJobStepControllerZZZ objController) throws ExceptionZZZ {
		super(objController);
		JobStepPageUploaderToFtpZZZ_(objKernel);
	}
	
	private boolean JobStepPageUploaderToFtpZZZ_(IKernelZZZ objKernel) throws ExceptionZZZ {
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
			try {
				//1. Hole die Interne Application für diesen Step.
	            //	 Dazu ist in der Ini Datei dieser STEP mit seinem Aliasnamen definiert (also NICHT die Klasse incl. Packagenamen) als ein Program.
	            //	 Die Werte dann im Program hinterlegen, also Pfad				
				IJobStepControllerZZZ objController = this.getJobStepController();
				
				//Falls etwas aus dem Job- oder de Application- oder dem aufrufenden Kernel-Objekt gebraucht wuerde:
				//IJobZZZ objJob = objController.getJob();				
				//IApplicationZZZ objApplication = objJob.getApplicationObject();
				//IKernelZZZ objKernel = objApplication.getKernelObject();
								
				//Ausgabeparameter des vorherigen HtmlTableWrite-Steps holen
				String sJobStepPrevious = "HtmlTableWriterStep";
				IJobStepOutputProviderZZZ objJobStepWithOutput = objController.getJobStepForOutput(sJobStepPrevious);
				if(objJobStepWithOutput==null) {
					String sLog = "Missing previous JobStep with output: '" + sJobStepPrevious + "' for this step '" + this.getJobStepAlias() + "'.";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				String sJobStepPreviousOutputParameter = "HtmlFile";
				IJobStepOutputZZZ objOutputStepPrevious = objJobStepWithOutput.getOutput(sJobStepPreviousOutputParameter);
				if(objOutputStepPrevious==null) {
					String sLog = "Missing JobStepPreviousOutputParameter: '" + sJobStepPreviousOutputParameter + "' from the step '" + sJobStepPrevious + "'.";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				File objFileToUpload = objOutputStepPrevious.getFile();
				if(objFileToUpload==null) {
					String sLog = "Missing File-Objekt from previos step: '" + sJobStepPrevious + "'.";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				if(!FileEasyZZZ.exists(objFileToUpload)) {
					String sLog = "File-Objekt from previos step: '" + sJobStepPrevious + "' does not exist. Path: '" + objFileToUpload.getAbsolutePath() + "'.";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				TODOGOON20230627;
				
				//### DEN STEP AUSFUEHREN...
                //Ziel ist es die Konfiguration des Programs 1:1 in das Modul aufzunehmen und dann in diesem Step wirklich nur das Program zu starten.
				//Vorhanden müssen also sein: FTP-Benutzer, FTP-Kennwort, FTP-Servername, Zielpfad auf dem Server, Zielname auf dem Server
				//Merke: Da wir direkt mit dem File-Objekt arbeiten, brauchen wir keine Angaben zum SourceFile-Pfad/-Namen.
				
				
				
				//TODOGOON20230620;//Problem, das alte Program braucht wohl ein JPanel
				//Daher sowieso ein neues Program
				//ProgramFileUploadZZZ objProgFileUpload = ....
				
				
				//altes Program
//				ProgramPageWebUploadUiOVPN objProgWebPageUpload = new ProgramPageWebUploadUiOVPN(objKernel, this.panel, this.saFlag4Program);
				
				//1. Ins Label schreiben, dass hier ein Update stattfindet
//				updateMessage(objProgWebPageUpload, "Uploading ...");
				
				//2. Hochladen der Webseite						
//				logLineDate("Uploading WebPage.");						
//				boolean bSuccessWebUpload = objProgWebPageUpload.uploadPageWeb();
//				
				
				
				//3. Diesen Wert wieder ins Label schreiben.
//				if(bSuccessWebUpload) {
//					updateMessage(objProgWebPageUpload,"Upload ended with success.");
//				}else {
//					updateMessage(objProgWebPageUpload,"Upload not successful, details in log.");
//				}
				
				
				

			} catch (Exception e) {
				e.printStackTrace();
			}									
		}//end main:
		return bReturn;
	}
}
