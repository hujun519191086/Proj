package cn.tlrfid.bean;

/**
 * 巡检结果的bean
 * 
 * @author sk
 * 
 */
public class PollingResult {
	
	private String name; // 检查的项目
	
	private boolean qualify; // 合格
	
	private boolean mistake; // 违规
	
	private String reason; // 违规理由
	
	private boolean ok; // 是否已经整改
	
	public boolean isOk() {
		return ok;
	}
	
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isQualify() {
		return qualify;
	}
	
	public void setQualify(boolean qualify) {
		this.qualify = qualify;
	}
	
	public boolean isMistake() {
		return mistake;
	}
	
	public void setMistake(boolean mistake) {
		this.mistake = mistake;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public PollingResult(String name, boolean qualify, boolean mistake, String reason) {
		super();
		this.name = name;
		this.qualify = qualify;
		this.mistake = mistake;
		this.reason = reason;
	}
	
	public PollingResult(String name, boolean qualify, boolean mistake, String reason, boolean ok) {
		super();
		this.name = name;
		this.qualify = qualify;
		this.mistake = mistake;
		this.reason = reason;
		this.ok = ok;
	}
	
	public PollingResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
