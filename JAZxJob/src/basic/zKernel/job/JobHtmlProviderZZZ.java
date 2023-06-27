package basic.zKernel.job;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelConfigZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.net.client.ConfigHtmlTableHandlerZZZ;
import basic.zKernel.net.client.IApplicationZZZ;

public class JobHtmlProviderZZZ extends AbstractJobZZZ{
	public static String sJOB_ALIAS="HtmlProviderJob";
	
	public JobHtmlProviderZZZ() throws ExceptionZZZ {	
		super();
	}
	
	public JobHtmlProviderZZZ(IApplicationZZZ objApplication) throws ExceptionZZZ {
		super(objApplication);
	}
	
	@Override
	public String getJobAliasCustom() throws ExceptionZZZ {
		return sJOB_ALIAS;
	}

	@Override
	public boolean process() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//1. Definiere die JobSteps und ihren Controller
			JobStepControllerZZZ objController = new JobStepControllerZZZ(this);
			ArrayList<IJobStepZZZ> listaStep = objController.getJobSteps();
			
			IKernelZZZ objKernelTableHandlerApplication = objController.getKernelObject();
			IKernelConfigZZZ objConfigTableHandlerApplication = objKernelTableHandlerApplication.getConfigObject();
			String sProjectPathCalling=objConfigTableHandlerApplication.getProjectPath();
			
			//DAS BACKEND handlebar machen, nutze anderes Modul, also eine andere ini-Datei...
			IKernelConfigZZZ objConfigTableHandlerModule = new ConfigHtmlTableHandlerZZZ();			
			objConfigTableHandlerModule.setCallingProjectPath(sProjectPathCalling);
			IKernelZZZ objKernelTableHandlerModule = new KernelZZZ(objConfigTableHandlerModule, (String) null); //Damit kann man über die Startparameter ein anders konfiguriertes Kernel-Objekt erhalten.
			//#####################################################
			
			//1.1 HtmlTableHead
			IJobStepOutputProviderZZZ stepHtmlTableHeader = new JobStepHtmlTableColumnHeaderZZZ(objKernelTableHandlerModule, objController);
			listaStep.add((IJobStepZZZ) stepHtmlTableHeader);
			
			//1.2 HtmlTableToFileWriter
			IJobStepOutputProviderZZZ stepHtmlReader = new JobStepHtmlTableWriteZZZ(objKernelTableHandlerModule, objController);
			listaStep.add(stepHtmlReader);
			
			//1.3 FileUploadToFtp
			IJobStepZZZ stepPageUploaderToFtp = new JobStepFileUploaderToFtpZZZ(objKernelTableHandlerModule, objController);
			listaStep.add(stepPageUploaderToFtp);
			
			//2. Verarbeiten
			boolean bSuccess = objController.process();				
			bReturn = bSuccess;
		}//end main:
		return bReturn;
	}
}
