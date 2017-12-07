package com.remote.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.kie.server.api.marshalling.Marshaller;
import org.kie.server.api.model.definition.ProcessDefinition;
import org.kie.server.api.model.instance.NodeInstance;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.QueryServicesClient;
import org.kie.server.client.UserTaskServicesClient;



public class RemoteClient {


	public static String serverUrl = "http://172.17.3.6:8080/kie-server/services/rest/server";

	public static String user = "adminkie";
	public static String password = "adminkie";
	
    
	public static String containerId = "Entrenamiento2";
	public static String processId = "Capacitacion.ListaDeAsistencia";
	
	public static Marshaller marshaller ;
	public static KieServicesConfiguration configuration;
	
	public static void main(String[] args) {
		
		
		//Crear la conexion contra kie - server
		configuration = KieServicesFactory.newRestConfiguration(serverUrl, user, password);
		configuration.setTimeout(30000L);
         
        KieServicesClient kieServicesClient =  KieServicesFactory.newKieServicesClient(configuration);
        
        QueryServicesClient queryClient = kieServicesClient.getServicesClient(QueryServicesClient.class);
        List<ProcessDefinition> processes = queryClient.findProcesses(0, 10);
        System.out.println("\t######### Available processes" + processes);
        
        ProcessServicesClient processClient = kieServicesClient.getServicesClient(ProcessServicesClient.class);
        // recuperar los procesos disponibles
        ProcessDefinition definition = processClient.getProcessDefinition(containerId, processId);
        System.out.println("\t######### Definition details: " + definition);
               
        //Configurar variables de entrada
        Map <String, Object> paramsProcces= new HashMap<String, Object>();
		paramsProcces.put("camino","camino b" );
		
		
        Long processInstanceId = processClient.startProcess(containerId, processId,paramsProcces);
        System.out.println("\t######### Process instance id: " + processInstanceId);
        
        List<NodeInstance> completedNodes = queryClient.findCompletedNodeInstances(processInstanceId, 0, 10);
        System.out.println("\t######### Completed nodes: " + completedNodes);
        
        
        UserTaskServicesClient taskClient = kieServicesClient.getServicesClient(UserTaskServicesClient.class);
        
        List<TaskSummary> tasks = taskClient.findTasksAssignedAsPotentialOwner(user, 0, 10);
        System.out.println("\t######### Tasks: " +tasks);
        
        
        Map <String, Object> paramsTask= new HashMap<String, Object>();
		paramsTask.put("correccion","camino b" );
        // complete task A
        Long taskId = tasks.get(0).getId();
        taskClient.startTask(containerId, taskId, user);
        taskClient.completeTask(containerId, taskId, user,paramsTask);
        
	}

}
