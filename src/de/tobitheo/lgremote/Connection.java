package de.tobitheo.lgremote;

import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class Connection {

	public static final int PORT = 8080;

	private final HttpClient httpclient = new DefaultHttpClient();

	private String host;
	private int sessionKey;

	public String mDnsDiscovery() throws IOException, SocketTimeoutException {
		DatagramSocket socket = new DatagramSocket();
		byte[] buffer = new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x0e, 0x5f, 0x6c, 0x67, 0x5f, 0x64, 0x74,
				0x76, 0x5f, 0x77, 0x69, 0x66, 0x69, 0x72, 0x63, 0x04, 0x5f,
				0x74, 0x63, 0x70, 0x05, 0x6c, 0x6f, 0x63, 0x61, 0x6c, 0x00,
				0x00, 0x0c, 0x00, 0x01 };
		DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length,
				new InetSocketAddress("224.0.0.251", 5353));

		socket.setBroadcast(true);
		socket.send(sendPacket);

		buffer = new byte[256];
		socket.setSoTimeout(1000);
		DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
		socket.receive(receivePacket);
		socket.close();
		
		return host = receivePacket.getAddress().toString().substring(1);
	}
	
	public void showAuthKey() throws ClientProtocolException, IOException {
		getRequest("/hdcp/api/data?target=version_info");
		postRequest(
				"/hdcp/api/auth",
				"<?xml version=\"1.0\" encoding=\"utf-8\"?><auth><type>AuthKeyReq</type></auth>");
	}

	public int authenticate(String authKey) throws ClientProtocolException,
			IOException {
		getRequest("/hdcp/api/data?target=version_info");
		String authResponse = postRequest("/hdcp/api/auth",
				"<?xml version=\"1.0\" encoding=\"utf-8\"?><auth><type>AuthReq</type><value>"
						+ authKey + "</value></auth>");
		String[] split = authResponse.split("</?session>");
		getRequest("/hdcp/api/data?target=context_ui&session=" + split[1]);
		return this.sessionKey = Integer.parseInt(split[1]);
	}

	public void sendKeyCode(Key key) throws ClientProtocolException, IOException {
		postRequest("/hdcp/api/dtv_wifirc", "<?xml version=\"1.0\" encoding=\"utf-8\"?><command><session>" + sessionKey + "</session><type>HandleKeyInput</type><value>" + key.getKeyCode() + "</value></command>");
	}

	public String sendKeyCode(int key) throws ClientProtocolException, IOException {
		return postRequest("/hdcp/api/dtv_wifirc", "<?xml version=\"1.0\" encoding=\"utf-8\"?><command><session>" + sessionKey + "</session><type>HandleKeyInput</type><value>" + key + "</value></command>");
	}

	private String getRequest(String path) throws ClientProtocolException,
			IOException {
		HttpGet httpget = new HttpGet("http://" + host + ":" + PORT + path);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		StringBuilder sb = new StringBuilder();
		if (entity != null) {
			InputStream instream = entity.getContent();
			for (int i = 0; i < entity.getContentLength(); ++i)
				sb.append((char) instream.read());
			instream.close();
		}
		return sb.toString();
	}

	private String postRequest(String path, String xml)
			throws ClientProtocolException, IOException {
		HttpPost httppost = new HttpPost("http://" + host + ":" + PORT + path);
		httppost.setEntity(new ByteArrayEntity(xml.getBytes()));
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		StringBuilder sb = new StringBuilder();
		if (entity != null) {
			InputStream instream = entity.getContent();
			for (int i = 0; i < entity.getContentLength(); ++i)
				sb.append((char) instream.read());
			instream.close();
		}
		return sb.toString();

	}
}
