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
	
	public JobHtmlProviderZZZ(IKernelZZZ objKernel, IApplicationZZZ objApplication) throws ExceptionZZZ{
		super(objKernel, objApplication);
	}
	
	
	@Override
	public String getJobAliasCustom() throws ExceptionZZZ {
		return sJOB_ALIAS;
	}

	@Override
	public ArrayList<IJobStepZZZ>createCustomJobSteps() throws ExceptionZZZ {
		ArrayList<IJobStepZZZ>listaReturn=null;
		main:{
			//1. Definiere die JobSteps (intern ueber ihren Controller)
			IKernelZZZ objKernelTableHandlerModule = this.getKernelObject();
			
			//1.1 HtmlTableHead
			IJobStepOutputProviderZZZ stepHtmlTableHeader = new JobStepHtmlTableColumnHeaderZZZ(objKernelTableHandlerModule, this.getJobStepController());
			this.addJobStep((IJobStepZZZ) stepHtmlTableHeader);
			
			//1.2 HtmlTableToFileWriter
			IJobStepOutputProviderZZZ stepHtmlReader = new JobStepHtmlTableWriteZZZ(objKernelTableHandlerModule, this.getJobStepController());
			this.addJobStep(stepHtmlReader);
			
			//1.3 FileUploadToFtp
			IJobStepZZZ stepPageUploaderToFtp = new JobStepFileUploaderToFtpZZZ(objKernelTableHandlerModule, this.getJobStepController());
			this.addJobStep(stepPageUploaderToFtp);
						
			listaReturn = this.getJobSteps();
		}//end main:
		return listaReturn;
	}

	//### aus IKernelModuleZZZ
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
}
