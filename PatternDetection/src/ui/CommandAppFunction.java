package ui;

import java.util.ArrayList;

import patternDetection.Tokenizer;
import ui.AppVar.AppBool;
import ui.AppVar.AppDouble;
import ui.AppVar.AppInt;
import ui.AppVar.AppNull;
import ui.AppVar.AppString;

//import java.util.ArrayList;

public abstract class CommandAppFunction {

	public static final AppNull NULL = AppVar.NULL;
	public static final AppString STR = AppVar.STR;
	public static final AppBool BOOL = AppVar.BOOL;
	public static final AppInt INT = AppVar.INT;
	public static final AppDouble DOUBLE = AppVar.DOUBLE;
	
	protected Tokenizer tokenizer;
	protected CommandApp app;
	protected final String name;
	
	public CommandAppFunction(CommandApp app, String name){
		this.app = app;
		this.name = name;
	}
	
	public abstract AppVar<? extends Object> call( ArrayList<AppVar<? extends Object>> args );
	
	public String getName(){
		return name;
	}
	
	public void badArgs(String check){
		this.app.showError("Bad arguments: \n"+check);
	}
	
	
	
	public static class quitFunction extends CommandAppFunction {

		public quitFunction(CommandApp app) {
			super(app, "quit");
		}

		@Override
		public AppVar<? extends Object> call(ArrayList<AppVar<? extends Object>> args) {
			if (args.size() != 0) this.app.showError("Unused arguments in 'quit'");
			this.app.quit();
			return NULL;
		}
	}
	
	public static class printFunction extends CommandAppFunction {
		public printFunction(CommandApp app) {
			super(app, "print");
		}

		@Override
		public AppVar<? extends Object> call(ArrayList<AppVar<? extends Object>> args) {
			for (AppVar<? extends Object> arg : args ){
				if (arg == NULL)
					this.app.out.print("NULL");
				else if (arg.get() == null)
					this.app.out.print(null);
				else
					this.app.out.print(arg.toString());
			}
			return NULL;
		}
	}
	
	public static class setEchoFunction extends CommandAppFunction {
		public setEchoFunction(CommandApp app) {
			super(app, "setEcho");
		}

		@Override
		public AppVar<? extends Object> call(ArrayList<AppVar<? extends Object>> args) {
			@SuppressWarnings("unchecked")
			String check = CommandAppUtil.argTypeCheck(args, BOOL);
			if (check.equals("")){
				this.app.echo = BOOL.convert(args.get(0));
			}else{
				this.app.showError(check);
			}
			return NULL;
		}
	}
	

	
	public static class concatFunction extends CommandAppFunction {
		public concatFunction(CommandApp app) {
			super(app, "concat");
		}

		@Override
		public AppVar<? extends Object> call(ArrayList<AppVar<? extends Object>> args) {
			String result = "";
			for (AppVar<? extends Object> arg : args)
				result += arg.toString();
			return new AppString(result);
		}
	}
	
	//Lists all the variables
	public static class lsFunction extends CommandAppFunction {
		public lsFunction(CommandApp app) {
			super(app, "ls");
		}

		@Override
		public AppVar<? extends Object> call(ArrayList<AppVar<? extends Object>> args) {
			if (args.size()!=0)
				this.app.showWarning("Unneeded arguments to ls");
			for (String s : this.app.variables.keySet())
				this.app.out.print(s);
			return NULL;
		}
	}
	
	public static class runFunction extends CommandAppFunction {
		public runFunction(CommandApp app) {
			super(app, "run");
		}

		@Override
		public AppVar<? extends Object> call(ArrayList<AppVar<? extends Object>> args) {
			String check = CommandAppUtil.argTypeCheck(args, STR);
			if (check.equals("")){
				String file = STR.convert(args.get(0));
				this.app.runFile(file);
				return NULL;
			}
			this.badArgs(check);
			return NULL;
		}
	}
	
	public static class pauseFunction extends CommandAppFunction {
		public pauseFunction(CommandApp app) {
			super(app, "pause");
		}

		@Override
		public AppVar<? extends Object> call(ArrayList<AppVar<? extends Object>> args) {
			this.app.out.print("PAUSE");
			this.app.in.getInput();
			return NULL;
		}
	}
	
	
	
	public static ArrayList<CommandAppFunction> getCommonFunctions(CommandApp app){
		ArrayList<CommandAppFunction> out = new ArrayList<CommandAppFunction>();
		out.add(new printFunction(app));
		out.add(new quitFunction(app));
		out.add(new setEchoFunction(app));
		out.add(new concatFunction(app));
		out.add(new lsFunction(app));
		out.add(new runFunction(app));
		out.add(new pauseFunction(app));
		
		return out;
	}
	
	
}
