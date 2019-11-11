import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * MyLeaveActivitiTest
 *
 *   基本工作流  流程
 *
 *
 *
 * @author Mr.zhou
 * @date 2019/11/7
 */
public class MyLeaveActivitiTest {

    /**
     *
     *   ProcessEngineConfiguration 创建流程处理数据表
     *
     *
     */
    @Test
    public void creatTableOne(){


        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
        processEngineConfiguration.setJdbcUrl("jdbc:mysql://172.22.43.24:3306/test");
        processEngineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
        processEngineConfiguration.setJdbcUsername("root");
        processEngineConfiguration.setJdbcPassword("123456");

        // setDatabaseSchema 设置在插入表的流程上，是先删除在插入还是直接插入数据库表
        processEngineConfiguration.setDatabaseSchema(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        //工作流处理引擎对象（核心对象processEngine）
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

    }


    /**
     *
     *   使用配置文件创建Activiti  23张的工作表
     *   ProcessEngine  重要的处理引擎对象
     *
     */
    @Test
    public void creatTable(){
        /**
         * 创建工作流的方法有很多，这里使用读取xml配置文件的方式
         *  还可以直接填写数据库的配置
         */
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
    }

    /**
    * @Description:  部署流程
    * @Param:
    * @return:
    * @Date: 2019/11/7
    */
    @Test
    public void deployProcess(){
        //ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder builder = repositoryService.createDeployment();
        builder.addClasspathResource("demo.bpmn");//bpmn文件的名称
        builder.addClasspathResource("demo.png");//bpmn文件的名称
        builder.deploy();
    }

    /**
    * @Description:  启动流程
    * @Param:
    * @return:
    * @Date: 2019/11/7
    */
    @Test
    public void startProcess(){
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();

        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance =   runtimeService.startProcessInstanceByKey("demo");//流程的名称，也可以使用ByID来启动流程
        System.out.println(processInstance.getId()+"结果="+processInstance.getName());
    }

    /** 
    * @Description:  查看流程定义 
    * @Param:  
    * @return: 
    * @Date: 2019/11/7 
    */ 
    @Test
    public void queryTask(){
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        //根据assignee(代理人)查询任务
        String assignee = "张三";
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).list();

        int size = tasks.size();
        for (int i = 0; i < size; i++) {
            Task task = tasks.get(i);

        }
        //首次运行的时候这个没有输出，因为第一次运行的时候扫描act_ru_task的表里面是空的，但第一次运行完成之后里面会添加一条记录，之后每次运行里面都会添加一条记录
        for (Task task : tasks) {
            System.out.println("taskId:" + task.getId() +
                    ",taskName:" + task.getName() +
                    ",assignee:" + task.getAssignee() +
                    ",createTime:" + task.getCreateTime());
        }
    }

    
    
    /**
     *   完成任务
     * 
     */
    @Test
    public void taskCom() {
    	
      String taskId = "7504";	
      //获取处理工作流引擎	
      ProcessEngine processEngine  = 	ProcessEngines.getDefaultProcessEngine();
      TaskService taskService =   processEngine.getTaskService();
      System.out.println(taskService.toString());
      taskService.complete(taskId);
      System.out.println("处理流程完毕！！！！");
    }
    
    /**
	 * 
	 *   流程部署定义
	 * 
	 */
  //  @Test
//   public void getProcess() {
//	   //获取工作流引擎    ProcessEngines静态方法实例
//	   ProcessEngine processEngie =  ProcessEngines.getDefaultProcessEngine();
//	    //获取仓库的实例                                                                        //部署流程定义
//	 Deployment deployment =   processEngie.getRepositoryService()
//			                 .createDeployment()   // 这个是流程定义
//			                  .addClasspathResource("helloword.bpmn").deploy();
//	 
//	 System.out.print(deployment.getId()+":: "+deployment.getName());
//   }

    
    
}
