package sapphire.common;


public class AppObject extends ObjectHandler {

	static final long serialVersionUID =553166605570253309L;

	@Override
	protected Class<?> getClass(Object obj) {
		return obj.getClass().getSuperclass();
	}

	public AppObject(Object obj) {
		super(obj);
	}
}
