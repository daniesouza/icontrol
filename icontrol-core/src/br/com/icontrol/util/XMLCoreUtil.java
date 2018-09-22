package br.com.icontrol.util;

/**
 * @author Icontrol
 * 
 */

public class XMLCoreUtil {

//	public static String mensagemToXML(Object obj) throws ConverterMensagemException {
//		try {
//			XStream xstream = new XStream();
//			configXStreamMensagem(xstream);
//			String xml = xstream.toXML(obj);
//			return xml;			
//		} catch (Exception e) {
//			throw new ConverterMensagemException(e.getMessage());
//		}
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static List<Mensagem> xmlToMensagem(String xml) throws ConverterMensagemException {
//		List<Mensagem> mensagens = new ArrayList<Mensagem>();
//		try {
//			XStream xstream = new XStream();
//			configXStreamMensagem(xstream);
//			mensagens = (List<Mensagem>) xstream.fromXML(xml);			
//		} catch (Exception e) {
//			try {
//				XStream xstream = new XStream();
//				configXStreamMensagem(xstream);
//				Mensagem msg = (Mensagem) xstream.fromXML(xml);
//				mensagens.add(msg);
//			} catch (Exception e2) {
//				throw new ConverterMensagemException(e2.getMessage());
//			}
//		}
//		return mensagens;
//	}	
//
//	private static void configXStreamMensagem(XStream xstream) {
//		xstream.setMode(XStream.NO_REFERENCES);
//		xstream.alias("mensagem", Mensagem.class);
//		xstream.alias("mensagens", List.class);
//	}

}