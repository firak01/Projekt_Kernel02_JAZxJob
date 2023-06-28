package basic.zKernel.job;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.component.AbstractKernelModuleZZZ;
import basic.zKernel.component.AbstractKernelProgramZZZ;
import basic.zKernel.net.client.IApplicationUserZZZ;
import basic.zKernel.net.client.IApplicationZZZ;

public abstract class AbstractJobZZZ extends AbstractKernelProgramZZZ implements IJobZZZ, IApplicationUserZZZ, IConstantZZZ{
	public static String sJOB_ALIAS="DEFAULT, IN ERBENDER KLASSE ERSETZEN";
	
	private String sAlias = null;
	private IApplicationZZZ objApplication = null;
	private IJobStepControllerZZZ objJobStepController = null;
	
	
	public AbstractJobZZZ() throws ExceptionZZZ  {	
		super();
	}
	
	public AbstractJobZZZ(IApplicationZZZ objApplication) throws ExceptionZZZ {
		super();
		AbstractJobNew_(objApplication);
	}
	
	public AbstractJobZZZ(IKernelZZZ objKernel, IApplicationZZZ objApplication) throws ExceptionZZZ {
		super(objKernel);
		AbstractJobNew_(objApplication);
	}
	
	private boolean AbstractJobNew_(IApplicationZZZ objApplication) throws ExceptionZZZ {
		this.setApplicationObject(objApplication);
					
		String sJobAlias = this.getJobAliasCustom();//Diese Methode soll auf die static Variable DER ERBENDEN Klasse zugreifen.
		this.setJobAlias(sJobAlias);
			
		ArrayList<IJobStepZZZ> listaJobStep = this.createCustomJobSteps();
		this.setJobSteps(listaJobStep);
		return true;
	}
	
	@Override
	public abstract String getJobAliasCustom() throws ExceptionZZZ;
	
	@Override
	public String getJobAlias() throws ExceptionZZZ {
		if(StringZZZ.isEmpty(this.sAlias)) {
			this.sAlias = this.getJobAliasCustom();
			if(StringZZZ.isEmpty(this.sAlias)) {
				this.sAlias = this.getJobAliasDefault();
			}
		}
		return this.sAlias;
	}
	
	@Override 
	public void setJobAlias(String sAlias) {
		this.sAlias = sAlias;
	}
	
	public String getJobAliasDefault() {
		return this.getClass().getCanonicalName();
	}
	
	
	//### Aus IJobZZZ
	@Override
	public IJobStepControllerZZZ getJobStepController() throws ExceptionZZZ {
		if(this.objJobStepController==null) {
			IJobStepControllerZZZ objController = new JobStepControllerZZZ(this);
			this.setJobStepController(objController);
		}
		return this.objJobStepController;
	}

	@Override
	public void setJobStepController(IJobStepControllerZZZ objJobStepController) {
		this.objJobStepController = objJobStepController;
	}
	
	@Override
	public ArrayList<IJobStepZZZ> getJobSteps() throws ExceptionZZZ{
		return this.getJobStepController().getJobSteps();		
	}
	
	@Override
	public void setJobSteps(ArrayList<IJobStepZZZ> listaJobStep) throws ExceptionZZZ{
		this.getJobStepController().setJobSteps(listaJobStep);
	}
	
	@Override
	public void addJobStep(IJobStepZZZ objJobStep) throws ExceptionZZZ{
		this.getJobStepController().addJobStep(objJobStep);
	}
	
	@Override
	public boolean process() throws ExceptionZZZ{
		return this.getJobStepController().process();
	}
	
	//### Aus IKernelUserZZZ
	@Override
	public IKernelZZZ getKernelObject() throws ExceptionZZZ {
		IKernelZZZ objKernel = super.getKernelObject();
		if(objKernel==null) {
			objKernel = this.getApplicationObject().getKernelObject();
			this.setKernelObject(objKernel);			
		}
		return objKernel;
	}
	
	//### Aus IApplicationUserZZZ
	@Override
	public IApplicationZZZ getApplicationObject() {
		return this.objApplication;
	}

	@Override
	public void setApplicationObject(IApplicationZZZ objApplication) {
		this.objApplication = objApplication;
	}
	
	@Override
	public abstract ArrayList<IJobStepZZZ> createCustomJobSteps() throws ExceptionZZZ;
}
