package com;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;

public class HelloWord {
	/**
	 * 
	 *   流程部署定义
	 * 
	 */
   public void getProcess() {
	   //获取工作流引擎
	   ProcessEngine processEngie =  ProcessEngines.getDefaultProcessEngine();
	    //获取仓库的实例
	 Deployment deployment =   processEngie.getRepositoryService()
			                 .createDeployment()   // 这个是流程定义
			                  .addClasspathResource("/resources/helloword.bpmn").deploy();
	 
	 System.out.print(deployment.getId()+":: "+deployment.getName());
   }

}
