package basic.zKernel.job;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.IKernelZZZ;
import custom.zKernel.LogZZZ;

public class JobStepControllerZZZ extends AbstractJobStepControllerZZZ {
	public JobStepControllerZZZ()throws ExceptionZZZ{
		super();
	}
	public JobStepControllerZZZ(IJobZZZ objJob)throws ExceptionZZZ{
		super(objJob);
	}
	
	
	
	public boolean process() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			ArrayList<IJobStepZZZ> listaStep = this.getJobSteps();
			
			//2. In einer Schleife abarbeiten
			int iStep = -1;
			for(IJobStepZZZ objStep : listaStep) {
				iStep++;
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + " - Index No. " + iStep + ": Executing Step '" + objStep.getJobStepAlias() + "'.");
				
				boolean bSuccess = objStep.process();
				if(!bSuccess) {
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + " - Index No. " + iStep + ": JobStep '" + objStep.getJobStepAlias() + "' did not complete successfully. Aborting.");
					break main;
				}else {
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + " - Index No. " + iStep + ": JobStep '" + objStep.getJobStepAlias() + "' completed successfully.");					
				}
								
			}
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	
}
