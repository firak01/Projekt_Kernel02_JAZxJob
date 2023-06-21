package basic.zKernel.job;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
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
			
			//1.1 HtmlTableHead
			IJobStepOutputProviderZZZ stepHtmlTableHeader = new JobStepHtmlTableColumnHeaderZZZ(objController);
			listaStep.add((IJobStepZZZ) stepHtmlTableHeader);
			
			//1.2 HtmlTableToFileWriter
			IJobStepOutputProviderZZZ stepHtmlReader = new JobStepHtmlTableWriteZZZ(objController);
			listaStep.add(stepHtmlReader);
			
			//1.3 FileUploadToFtp
			IJobStepZZZ stepPageUploaderToFtp = new JobStepFileUploaderToFtpZZZ(objController);
			listaStep.add(stepPageUploaderToFtp);
			
			//2. Verarbeiten
			boolean bSuccess = objController.process();
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
}
