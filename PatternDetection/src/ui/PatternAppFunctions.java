package ui;

import static ui.CommandAppUtil.argTypeCheck;
import static ui.CommandAppUtil.argTypeCheckRepeat;

import java.util.ArrayList;

import patternDetection.EvaluationObject;
import patternDetection.EvaluationObject.EvaluationSettings;
import patternDetection.Interval;
import patternDetection.Match;
import patternDetection.Pattern;
import patternDetection.PatternCompleter;
import patternDetection.PatternExtractor;
import patternDetection.SimpleToken;
import patternDetection.SimpleTokenStream;
import ui.AppVar.AppBool;
import ui.AppVar.AppDouble;
import ui.AppVar.AppInt;
import ui.AppVar.AppNull;
import ui.AppVar.AppString;
import ui.PatternAppVarTypes.AppEvObj;
import ui.PatternAppVarTypes.AppList;
import ui.PatternAppVarTypes.AppMatch;
import ui.PatternAppVarTypes.AppPattern;
import ui.PatternAppVarTypes.AppStream;
import ui.PatternAppVarTypes.AppTable;
import ui.PatternAppVarTypes.AppToken;
import behaviorClassification.CsvToTable;
import behaviorClassification.RawTimeSeriesTable;

public class PatternAppFunctions {

	
	public static final AppNull NULL = AppVar.NULL;
	public static final AppString STR = AppVar.STR;
	public static final AppBool BOOL = AppVar.BOOL;
	public static final AppInt INT = AppVar.INT;
	public static final AppDouble DOUBLE = AppVar.DOUBLE;
	
	public static final AppTable TAB = PatternAppVarTypes.TAB;
	public static final AppStream STREAM = PatternAppVarTypes.STREAM;
	public static final AppPattern PAT = PatternAppVarTypes.PAT;
	public static final AppList LIST = PatternAppVarTypes.LIST;
	public static final AppEvObj EV_OBJ = PatternAppVarTypes.EV_OBJ;
	public static final AppMatch MATCH = PatternAppVarTypes.MATCH;
	
	

	
	
	public static class parsePatternFunction extends CommandAppFunction {
		public parsePatternFunction(CommandApp app) {
			super(app, "parsePattern");
		}

		@Override
		public AppVar<? extends Object> call(ArrayList<AppVar<? extends Object>> args) {
			String check = argTypeCheck(args, STR);
			if (check.equals("")){
				Pattern p = PatternParser.parse( STR.convert(args.get(0)) );
				if (p == null){
					this.app.showWarning("Pattern was not parsable");
					return NULL;
				}
				return new AppPattern(p);
			}
			
			this.app.showError(check);
			return NULL;
		}
	}
	
	
	public static class readTableFunction extends CommandAppFunction {
		public readTableFunction(CommandApp app) {
			super(app, "readTable");
		}

		@Override
		public AppVar<? extends Object> call(ArrayList<AppVar<? extends Object>> args) {
			String error;
			String check = argTypeCheck(args, STR);
			String fileName;
			if (check.equals("")){
				fileName = this.app.getDataPath() + STR.convert(args.get(0)) ;
				return new AppTable( CsvToTable.readCsv( fileName ) );
			}else{
				error = check;
				check = argTypeCheck(args, STR, DOUBLE, DOUBLE);
				fileName = this.app.getDataPath() + STR.convert(args.get(0)) ;

				if (check.equals("")){
					return new AppTable( CsvToTable.readCsv( fileName,
							DOUBLE.convert(args.get(1)),
							DOUBLE.convert(args.get(2)) ));
				}
			}
			this.app.showError("Bad arguments for function "+this.getName() );
			this.app.out.print(error);
			return NULL;
		}
	}
	

	public static class subTableFunction extends CommandAppFunction {
		public subTableFunction(CommandApp app) {
			super(app, "subTable");
		}

		@Override
		public AppVar<? extends Object> call(ArrayList<AppVar<? extends Object>> args) {
			String error;
			String check = argTypeCheck(args, TAB, DOUBLE, DOUBLE);
			if (check.equals("")){
				RawTimeSeriesTable table = TAB.convert(args.get(0));
				Double t1 = DOUBLE.convert(args.get(1));
				Double t2 = DOUBLE.convert(args.get(2));
				table = table.subTable(t1, t2);
				return new AppTable(table);
			}else{
				error = check;
				AppVar<? extends Object> table = null;
				if (args.size()>=1)
					table = args.remove(0);
				
				check = argTypeCheckRepeat(args, STR);
				if (TAB.instance(table)){
					if (check.equals("")){
						RawTimeSeriesTable outTable = TAB.convert(table);
						String[] headers = new String[args.size()];
						for (int i=0; i<headers.length; i++)
							headers[i] = STR.convert(args.get(i));
						outTable = outTable.subTable(headers);
						return new AppTable(outTable);
					}
					this.app.showError("Bad types: "+error+"\n"+check);
				}
				this.app.showError("Bad types: "+error+"\n"+check);
			}
			return NULL;
		}
	}
	
	
	public static class renTabColFunction extends CommandAppFunction {
		public renTabColFunction(CommandApp app) {
			super(app, "renTabCol");
		}

		@Override
		public AppVar<? extends Object> call(ArrayList<AppVar<? extends Object>> args) {
			String check = argTypeCheck(args, TAB, STR, STR);
			if (check.equals("")){
				RawTimeSeriesTable table = TAB.convert(args.get(0));
				String cur = STR.convert(args.get(1));
				String fin = STR.convert(args.get(2));
				table.rename(cur, fin);
				return args.get(0);
			}
			this.app.showError("Bad arguments: "+check);
			return NULL;
		}
	}
	

	
	public static class tokenizeFunction extends CommandAppFunction {
		public tokenizeFunction(CommandApp app) {
			super(app, "tokenize");
		}

		@Override
		public AppVar<? extends Object> call(ArrayList<AppVar<? extends Object>> args) {
			String check = argTypeCheck(args, TAB, INT);
			if (check.equals("")){
				RawTimeSeriesTable table = TAB.convert(args.get(0));
				int numChunks = INT.convert(args.get(1));
				SimpleTokenStream stream = this.app.tokenizer.tokenize(table, numChunks);
				return new AppStream(stream);
			}else{
				String error = check;
				check = argTypeCheck(args, TAB, DOUBLE);
				if (check.equals("")){
					RawTimeSeriesTable table = TAB.convert(args.get(0));
					Double chunkWidth = DOUBLE.convert(args.get(1));
					SimpleTokenStream stream = this.app.tokenizer.tokenize(table, chunkWidth);
					return new AppStream(stream);
				}
				this.badArgs(error + "\n" +check);
				return NULL;
			}
//			return NULL;
		}
	}
	
	public static class matchFunction extends CommandAppFunction {
		public matchFunction(CommandApp app) {
			super(app, "match");
		}

		@Override
		public AppVar<? extends Object> call(ArrayList<AppVar<? extends Object>> args) {
			String check = argTypeCheck(args, STREAM, PAT);
			if (check.equals("")){
				SimpleTokenStream stream = STREAM.convert(args.get(0));
				Pattern p = PAT.convert(args.get(1));
				EvaluationObject eo = EvaluationObject.evaluate(stream, p);
				return new AppEvObj(eo);
			}
			this.badArgs(check);	
			return NULL;
		}
	}
	
	
	public static class completeTimeFunction extends CommandAppFunction {
		public completeTimeFunction(CommandApp app) {
			super(app, "completeTime");
		}

		@Override
		public AppVar<? extends Object> call(ArrayList<AppVar<? extends Object>> args) {
			String check = argTypeCheck(args, STREAM, PAT);
			if (check.equals("")){
				SimpleTokenStream sts = STREAM.convert(args.get(0));
				Pattern p = PAT.convert(args.get(1));
				EvaluationSettings settings = this.app.getEvalSettings();
				Interval i = PatternCompleter.completeTime(sts, p.pre, p.suc, settings);
				Pattern out = new Pattern(p.pre, p.suc, i);
				return new AppPattern(out);
			}
			this.badArgs(check);
			return NULL;
		}
	}

	
	
	
	public static class extractFunction extends CommandAppFunction {
		public extractFunction(CommandApp app) {
			super(app, "extract");
		}

		@Override
		public AppVar<? extends Object> call(ArrayList<AppVar<? extends Object>> args) {
			String check = argTypeCheck(args, STREAM);
			EvaluationSettings settings = this.app.getEvalSettings();
			
			if (check.equals("")){
				SimpleTokenStream sts = STREAM.convert(args.get(0));
				int n = this.app.getNumPatterns();

				PatternExtractor extractor = new PatternExtractor(settings);
				
				ArrayList<EvaluationObject> patterns = extractor.extract(sts, n);
				
				ArrayList<AppEvObj> patterns2 = new ArrayList<PatternAppVarTypes.AppEvObj>();
				for (EvaluationObject eo : patterns){
					patterns2.add( new AppEvObj(eo) );
				}
				
				AppList list = new AppList(patterns2);
				return list;
			}else{
				String error = check;
				check = argTypeCheck(args, STREAM, INT);
				if (check.equals("")){
					SimpleTokenStream sts = STREAM.convert(args.get(0));
					int n = INT.convert(args.get(1));
					PatternExtractor extractor = new PatternExtractor(settings);
					
					ArrayList<EvaluationObject> patterns = extractor.extract(sts, n);
					
					ArrayList<AppEvObj> patterns2 = new ArrayList<PatternAppVarTypes.AppEvObj>();
					for (EvaluationObject eo : patterns){
						patterns2.add( new AppEvObj(eo) );
					}
					
					AppList list = new AppList(patterns2);
					return list;
					
				}else{
					this.badArgs(error + "\n" + check);
				}
			}
			
			return NULL;
		}
	}

	
	public static class getIndFunction extends CommandAppFunction {
		public getIndFunction(CommandApp app) {
			super(app, "getInd");
		}

		@Override
		public AppVar<? extends Object> call(ArrayList<AppVar<? extends Object>> args) {
			String check = argTypeCheck(args, LIST, INT);
			if (check.equals("")){
				ArrayList<? extends AppVar<? extends Object>> list = LIST.convert(args.get(0));
				int ind = INT.convert(args.get(1));
				return list.get(ind);
			}
			this.badArgs(check);
			return NULL;
		}
	}
	
	public static class getMatchesFunction extends CommandAppFunction {
		public getMatchesFunction(CommandApp app) {
			super(app, "getMatches");
		}

		@Override
		public AppVar<? extends Object> call(ArrayList<AppVar<? extends Object>> args) {
			String check = argTypeCheck(args, EV_OBJ);
			if (check.equals("")){
				EvaluationObject eo = EV_OBJ.convert(args.get(0));
				ArrayList<Match> matches = eo.getMdo().getMatches();
				ArrayList<AppMatch> list = new ArrayList<AppMatch>();
				for (Match m : matches)
					list.add(new AppMatch(m));
				return new AppList(list);
			}
			this.badArgs(check);
			return NULL;
		}
	}
	
	public static class getAntiMatchesFunction extends CommandAppFunction {
		public getAntiMatchesFunction(CommandApp app) {
			super(app, "getAntiMatches");
		}

		@Override
		public AppVar<? extends Object> call(ArrayList<AppVar<? extends Object>> args) {
			String check = argTypeCheck(args, EV_OBJ);
			if (check.equals("")){
				EvaluationObject eo = EV_OBJ.convert(args.get(0));
				ArrayList<Match> matches = eo.getMdo().getAntiMatches();
				ArrayList<AppMatch> list = new ArrayList<AppMatch>();
				for (Match m : matches)
					list.add(new AppMatch(m));
				return new AppList(list);
			}
			this.badArgs(check);
			return NULL;
		}
	}
	
	public static class getIndMatchesFunction extends CommandAppFunction {
		public getIndMatchesFunction(CommandApp app) {
			super(app, "getIndMatches");
		}

		@Override
		public AppVar<? extends Object> call(ArrayList<AppVar<? extends Object>> args) {
			String check = argTypeCheck(args, EV_OBJ);
			if (check.equals("")){
				EvaluationObject eo = EV_OBJ.convert(args.get(0));
				ArrayList<Match> matches = eo.getMdo().getIndMatches();
				ArrayList<AppMatch> list = new ArrayList<AppMatch>();
				for (Match m : matches)
					list.add(new AppMatch(m));
				return new AppList(list);
			}
			this.badArgs(check);
			return NULL;
		}
	}
	
	public static class getPreFunction extends CommandAppFunction {
		public getPreFunction(CommandApp app) {
			super(app, "getPre");
		}

		@Override
		public AppVar<? extends Object> call(ArrayList<AppVar<? extends Object>> args) {
			String check = argTypeCheck(args, EV_OBJ);
			if (check.equals("")){
				EvaluationObject eo = EV_OBJ.convert(args.get(0));
				ArrayList<SimpleToken> tokens = eo.getMdo().getPrecursors();
				ArrayList<AppToken> list = new ArrayList<AppToken>();
				for (SimpleToken t : tokens)
					list.add(new AppToken(t));
				return new AppList(list);
			}
			this.badArgs(check);
			return NULL;
		}
	}
	
	public static class getSucFunction extends CommandAppFunction {
		public getSucFunction(CommandApp app) {
			super(app, "getSuc");
		}

		@Override
		public AppVar<? extends Object> call(ArrayList<AppVar<? extends Object>> args) {
			String check = argTypeCheck(args, EV_OBJ);
			if (check.equals("")){
				EvaluationObject eo = EV_OBJ.convert(args.get(0));
				ArrayList<SimpleToken> tokens = eo.getMdo().getSuccessors();
				ArrayList<AppToken> list = new ArrayList<AppToken>();
				for (SimpleToken t : tokens)
					list.add(new AppToken(t));
				return new AppList(list);
			}
			this.badArgs(check);
			return NULL;
		}
	}
	
	
	
	

	
	
	public static ArrayList<CommandAppFunction> getPatternAppFunctions(CommandApp app){
		ArrayList<CommandAppFunction> out = new ArrayList<CommandAppFunction>();
		out.add(new parsePatternFunction(app));
		out.add(new readTableFunction(app));
		out.add(new subTableFunction(app));
		out.add(new renTabColFunction(app));
		out.add(new tokenizeFunction(app));
		out.add(new matchFunction(app));
		out.add(new completeTimeFunction(app));
		out.add(new extractFunction(app));
		out.add(new getIndFunction(app));
		out.add(new getMatchesFunction(app));
		out.add(new getAntiMatchesFunction(app));
		out.add(new getPreFunction(app));
		out.add(new getSucFunction(app));
		return out;
	}
	
	
	
	

	
	public static class displayFunction extends CommandAppFunction {
		public displayFunction(CommandApp app) {
			super(app, "display");
		}

		@Override
		public AppVar<? extends Object> call(ArrayList<AppVar<? extends Object>> args) {
			String check = argTypeCheck(args, STREAM);
			if (check.equals("")){
				SimpleTokenStream sts = STREAM.convert(args.get(0));
				this.app.displayStream(sts);
				return NULL;
			}else{
				String error = check;
				check = argTypeCheck(args, STREAM, MATCH);
				if (check.equals("")){
					SimpleTokenStream sts = STREAM.convert(args.get(0));
					Match m = MATCH.convert(args.get(1));
					sts = sts.subStream(m);
					this.app.displayStream(sts);
					return NULL;
				}
				this.badArgs(error + "\n" + check);
				return NULL;
			}
			
//			return NULL;
		}
	}
	
	
	
	
	public static ArrayList<CommandAppFunction> getPatternAppGuiFunctions(CommandApp app){
		ArrayList<CommandAppFunction> out = new ArrayList<CommandAppFunction>();
		out.add(new displayFunction(app));
		
		return out;
	}
	
	
}
