import jade.core.AID;
import jade.core.behaviours.DataStore;
import jade.core.behaviours.FSMBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.List;

public class FSMProtocolo extends FSMBehaviour{

	public static final String AID_OPONENTE = "aid-oponente";
	public static final String PROPOSE_INITIAL = "propose-initial";
	private static final String ENVIAR_PROPUESTA = "enviar-propuesta";
	private static final String EVALUAR_PROPUESTA = "evaluar-propuesta";
	private static final String ESPERAR_RESPUESTA = "esperar-respuesta";
	private static final String ENVIAR_ZEUTHEN = "enviar-zeuthen";
	private static final String RECIBIR_ZEUTHEN ="recibir-zeuthen";
	private static final String ESPERAR_PROPUESTA = "esperar-propuesta";
	private static final String ACUERDO = "acuerdo";
	private static final String CONFLICTO = "conflicto";
	public static final String ULTIMOMSJ = "ultimo-msg";
	public static final String MSJ_ENVIADO = "mensaje-enviado";
	// INICIATOR
	public FSMProtocolo(AID aid) {
		DataStore ds = new DataStore();
		ds.put(AID_OPONENTE, aid);

		this.crearFSM(ds, true);

	}
	
	// RESPONDER
	public FSMProtocolo(ACLMessage proposeInicial) {
		DataStore ds = new DataStore();
		ds.put(ULTIMOMSJ, proposeInicial);

		this.crearFSM(ds, false);

	}

	private void crearFSM(DataStore ds, boolean ini) {

		// Instanciamos los comportamientos
		EnviarPropuesta enviar_propuesta = new EnviarPropuesta();
		enviar_propuesta.setDataStore(ds);

		EsperarRespuesta esperar_respuesta = new EsperarRespuesta();
		esperar_respuesta.setDataStore(ds);

		EnviarZeuthen enviar_zeuthen = new EnviarZeuthen();
		enviar_zeuthen.setDataStore(ds);

		RecibirZeuthen recibir_zeuthen = new RecibirZeuthen();
		recibir_zeuthen.setDataStore(ds);

		EsperarPropuesta esperar_propuesta = new EsperarPropuesta();
		esperar_propuesta.setDataStore(ds);

		EvaluarPropuesta evaluar_propuesta = new EvaluarPropuesta();
		evaluar_propuesta.setDataStore(ds);

		Acuerdo acuerdo = new Acuerdo();
		acuerdo.setDataStore(ds);

		Conflicto conflicto = new Conflicto();
		conflicto.setDataStore(ds);

		// Definir los estados de la FSM
		if (ini) { // Initiator
			this.registerFirstState(enviar_propuesta, ENVIAR_PROPUESTA);
			this.registerState(evaluar_propuesta, EVALUAR_PROPUESTA);
		}
		else { // Responder
			this.registerFirstState(evaluar_propuesta, EVALUAR_PROPUESTA);
			this.registerState(enviar_propuesta, ENVIAR_PROPUESTA);
		}

		this.registerState(esperar_respuesta, ESPERAR_RESPUESTA);

		this.registerState(enviar_zeuthen, ENVIAR_ZEUTHEN);
		
		this.registerState(recibir_zeuthen, RECIBIR_ZEUTHEN);
		
		this.registerState(esperar_propuesta, ESPERAR_PROPUESTA);
		
		this.registerLastState(acuerdo, ACUERDO);
		
		this.registerLastState(conflicto, CONFLICTO);
		
		// Definir transiciones

		String [] toReset_EnviarPropuesta= {ENVIAR_PROPUESTA};
		String [] toReset_EsperarRespuesta = {ESPERAR_RESPUESTA};
		String [] toReset_EsperarPropuesta = {ESPERAR_PROPUESTA};
		String [] toReset_EvaluarPropuesta = {EVALUAR_PROPUESTA};
		String [] toReset_RecibirZeuthen = {RECIBIR_ZEUTHEN};


		this.registerTransition(ENVIAR_PROPUESTA, ESPERAR_RESPUESTA, 0, toReset_EsperarRespuesta);
		this.registerTransition(ENVIAR_PROPUESTA, CONFLICTO, 1); // No tengo m??s propuestas y debo conceder
		
		this.registerTransition(ESPERAR_RESPUESTA, ENVIAR_ZEUTHEN, 0); // Reject
		this.registerTransition(ESPERAR_RESPUESTA, ACUERDO, 1); // Accept
		
		this.registerDefaultTransition(ENVIAR_ZEUTHEN, RECIBIR_ZEUTHEN,toReset_RecibirZeuthen);
		
		this.registerTransition(RECIBIR_ZEUTHEN, ESPERAR_PROPUESTA, 0,toReset_EsperarPropuesta); // Mi Z es Mayor
		this.registerTransition(RECIBIR_ZEUTHEN, ENVIAR_PROPUESTA, 1,toReset_EnviarPropuesta); // Mi Z es Menor
		
		this.registerTransition(ESPERAR_PROPUESTA, EVALUAR_PROPUESTA, 0,toReset_EvaluarPropuesta); // Recib?? propuesta
		this.registerTransition(ESPERAR_PROPUESTA, CONFLICTO, 1); // Recib?? Cancel
		
		this.registerTransition(EVALUAR_PROPUESTA, ENVIAR_ZEUTHEN, 0); // Reject
		this.registerTransition(EVALUAR_PROPUESTA, ACUERDO, 1); // Accept


	}

}
