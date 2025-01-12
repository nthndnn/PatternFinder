package ui;

public abstract class AppVar<T> {
	
	
	public static final AppNull NULL = new AppNull();
	
	public static final AppString STR = new AppString(null);
	public static final AppBool BOOL = new AppBool(null);
	public static final AppInt INT = new AppInt(null);
	public static final AppDouble DOUBLE = new AppDouble(null);
//	public static final AppInt INT = new AppInt(null);
	

//	String name;
	T obj;
//	public String getName(){
//		return name;
//	}
	
	public AppVar(T arg){ 
		this.obj = arg;
	}
	
	public T get(){
		return obj;
	}
	
	public abstract String getType();
	
	public abstract boolean instance(AppVar<? extends Object> appVar);

	public abstract T convert(AppVar<? extends Object> appVar);
	
	public String toString(){
		return obj.toString();
	}
	
	
	//Basic types:
	
	public static class AppString extends AppVar<String>{
		public AppString(String s){
			super(s);
		}

		public boolean instance(AppVar<? extends Object> appVar) {
			return appVar instanceof AppString;
		}

		public String convert(AppVar<? extends Object> appVar) {
			if (instance(appVar))
				return ((AppString)appVar).get();
			return null;
		}

		@Override
		public String getType() {
			return "String";
		}
	}
	
	public static class AppBool extends AppVar<Boolean>{
		public static final AppBool TRUE = new AppBool(true);
		public static final AppBool FALSE = new AppBool(false);
		private AppBool(Boolean b){
			super(b);
		}
		
		public boolean instance(AppVar<? extends Object> appVar) {
			return appVar instanceof AppBool;
		}

		public Boolean convert(AppVar<? extends Object> appVar) {
			if (instance(appVar))
				return ((AppBool)appVar).get();
			return null;
		}

		@Override
		public String getType() {
			return "Boolean";
		}
	}
	
	public static class AppDouble extends AppVar<Double>{
		public AppDouble(Double d){
			super(d);
		}
		
		public boolean instance(AppVar<? extends Object> appVar) {
			return appVar instanceof AppDouble || appVar instanceof AppInt;
		}

		public Double convert(AppVar<? extends Object> appVar) {
//			if (instance(appVar))
//				return ((AppDouble)appVar).get();
			if (appVar instanceof AppDouble)
				return ((AppDouble)appVar).get();
			if (appVar instanceof AppInt)
				return ((AppInt)appVar).get() + 0.0;	//Convert to Double
			
			return null;
		}

		@Override
		public String getType() {
			return "Double";
		}
	}
	
	
	
	public static class AppInt extends AppVar<Integer>{
		public AppInt(Integer i){
			super(i);
		}
		
		public boolean instance(AppVar<? extends Object> appVar) {
			return appVar instanceof AppInt;
		}

		public Integer convert(AppVar<? extends Object> appVar) {
			if (instance(appVar))
				return ((AppInt)appVar).get();
			return null;
		}

		@Override
		public String getType() {
			return "Integer";
		}
	}
	
//	public static class AppVarName extends AppVar<AppVar<? extends Object>>{
//		String name;
//		public AppVarName(String name, AppVar<? extends Object> arg) {
//			super(arg);
//			this.name = name;
//		}
//		
//		public AppVar<? extends Object> eval(){ 
//			return this.get();
//		}
//		
//		public static boolean instance(AppVar<? extends Object> appVar) {
//			return appVar instanceof AppVarName;
//		}
//	}
	
	public static class AppNull extends AppVar<Object>{
		private AppNull(){
			super(null);
		}
		@Override
		public String getType() {
			return "NULL";
		}
		@Override
		public boolean instance(AppVar<? extends Object> appVar) {
			return false;
		}
		@Override
		public Object convert(AppVar<? extends Object> appVar) {
			return null;
		}
		public String toString(){
			return "";
		}
	}
	
}


