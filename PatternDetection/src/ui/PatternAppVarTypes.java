package ui;

import java.util.ArrayList;

import patternDetection.EvaluationObject;
import patternDetection.Match;
import patternDetection.MatchDataObject;
import patternDetection.Pattern;
import patternDetection.SimpleToken;
import patternDetection.SimpleTokenStream;
import patternDetection.Tokenizer;
import behaviorClassification.RawTimeSeriesTable;



/**
 * A dummy file to hold Pattern related AppVar's
 * @author nathandunn
 *
 */
public abstract class PatternAppVarTypes {
	
	public static final AppTable TAB = new AppTable(null);
	public static final AppTokenizer TOK = new AppTokenizer(null);
	public static final AppStream STREAM = new AppStream(null);
	public static final AppPattern PAT = new AppPattern(null);
	public static final AppList LIST = new AppList(null);
	public static final AppEvObj EV_OBJ = new AppEvObj(null);
	public static final AppMatch MATCH = new AppMatch(null);

	
	
	public static class AppPattern extends AppVar<Pattern>{
		public AppPattern(Pattern arg) {
			super(arg);
		}

		@Override
		public String getType() {
			return "Pattern";
		}

		@Override
		public boolean instance(AppVar<? extends Object> appVar) {
			return appVar instanceof AppPattern;
		}

		@Override
		public Pattern convert(AppVar<? extends Object> appVar) {
			if (instance(appVar))
				return ((AppPattern)appVar).get();
			return null;
		}
	}
	
	public static class AppStream extends AppVar<SimpleTokenStream>{

		public AppStream(SimpleTokenStream arg) {
			super(arg);
		}

		@Override
		public String getType() {
			// TODO Auto-generated method stub
			return "SimpleTokenStream";
		}

		@Override
		public boolean instance(AppVar<? extends Object> appVar) {
			return appVar instanceof AppStream;
		}

		@Override
		public SimpleTokenStream convert(AppVar<? extends Object> appVar) {
			if (instance(appVar))
				return ((AppStream)appVar).get();
			return null;
		}
	}
	
	public static class AppTable extends AppVar<RawTimeSeriesTable>{

		public AppTable(RawTimeSeriesTable arg) {
			super(arg);
		}

		@Override
		public String getType() {
			return "RawTimeSeriesTable";
		}

		@Override
		public boolean instance(AppVar<? extends Object> appVar) {
			return appVar instanceof AppTable;
		}

		@Override
		public RawTimeSeriesTable convert(AppVar<? extends Object> appVar) {
			if (instance(appVar))
				return ((AppTable)appVar).get();
			return null;
		}
	}
	
	
	public static class AppToken extends AppVar<SimpleToken>{

		public AppToken(SimpleToken arg) {
			super(arg);
		}

		@Override
		public String getType() {
			return "SimpleToken";
		}

		@Override
		public boolean instance(AppVar<? extends Object> appVar) {
			return appVar instanceof AppToken;
		}

		@Override
		public SimpleToken convert(AppVar<? extends Object> appVar) {
			if (instance(appVar))
				return ((AppToken)appVar).get();
			return null;
		}
	}
	
	public static class AppTokenizer extends AppVar<Tokenizer>{

		public AppTokenizer(Tokenizer arg) {
			super(arg);
		}

		@Override
		public String getType() {
			return "Tokenizer";
		}

		@Override
		public boolean instance(AppVar<? extends Object> appVar) {
			return appVar instanceof AppTokenizer;
		}

		@Override
		public Tokenizer convert(AppVar<? extends Object> appVar) {
			if (instance(appVar))
				return ((AppTokenizer)appVar).get();
			return null;
		}
	}


	
	
	public static class AppMatch extends AppVar<Match>{

		public AppMatch(Match arg) {
			super(arg);
		}

		@Override
		public String getType() {
			return "Match";
		}

		@Override
		public boolean instance(AppVar<? extends Object> appVar) {
			return appVar instanceof AppMatch;
		}

		@Override
		public Match convert(AppVar<? extends Object> appVar) {
			if (instance(appVar))
				return ((AppMatch)appVar).get();
			return null;
		}
	}
	
	
	public static class AppMdo extends AppVar<MatchDataObject>{

		public AppMdo(MatchDataObject arg) {
			super(arg);
		}

		@Override
		public String getType() {
			return "MatchDataObject";
		}

		@Override
		public boolean instance(AppVar<? extends Object> appVar) {
			return appVar instanceof AppMdo;
		}

		@Override
		public MatchDataObject convert(AppVar<? extends Object> appVar) {
			if (instance(appVar))
				return ((AppMdo)appVar).get();
			return null;
		}
	}
	
	public static class AppEvObj extends AppVar<EvaluationObject>{

		public AppEvObj(EvaluationObject arg) {
			super(arg);
		}

		@Override
		public String getType() {
			return "EvaluationObject";
		}

		@Override
		public boolean instance(AppVar<? extends Object> appVar) {
			return appVar instanceof AppEvObj;
		}

		@Override
		public EvaluationObject convert(AppVar<? extends Object> appVar) {
			if (instance(appVar))
				return ((AppEvObj)appVar).get();
			return null;
		}
	}
	
//	public static class AppEvObjList extends AppVar<ArrayList<EvaluationObject>>{
//
//		public AppEvObjList(ArrayList<EvaluationObject> arg) {
//			super(arg);
//		}
//
//		@Override
//		public String getType() {
//			return "ArrayList<EvaluationObject>";
//		}
//
//		@Override
//		public boolean instance(AppVar<? extends Object> appVar) {
//			return appVar instanceof AppEvObjList;
//		}
//
//		@Override
//		public ArrayList<EvaluationObject> convert(AppVar<? extends Object> appVar) {
//			if (instance(appVar))
//				return ((AppEvObjList)appVar).get();
//			return null;
//		}
//	}
	
//	public static class AppList extends AppVar<ArrayList<AppVar<? extends Object>>>{
//
//		public AppList(ArrayList<AppVar<? extends Object>> arg) {
//			super(arg);
//		}
//
//		@Override
//		public String getType() {
//			return "ArrayList<AppVar<? extends Object>>";
//		}
//
//		@Override
//		public boolean instance(AppVar<? extends Object> appVar) {
//			return appVar instanceof AppList;
//		}
//
//		@Override
//		public ArrayList<AppVar<? extends Object>> convert(AppVar<? extends Object> appVar) {
//			if (instance(appVar))
//				return ((AppList)appVar).get();
//			return null;
//		}
//	}
	
//	public static class AppList extends AppVar<ArrayList<? extends AppVar<?>>>{
//
//		public AppList(ArrayList<? extends AppVar<?>> arg) {
//			super(arg);
//		}
//
//		@Override
//		public String getType() {
//			return "ArrayList<? extends AppVar<? extends Object>>";
//		}
//
//		@Override
//		public boolean instance(AppVar<? extends Object> appVar) {
//			return appVar instanceof AppList;
//		}
//
//		@Override
//		public ArrayList<? extends AppVar<? extends Object>> convert(AppVar<? extends Object> appVar) {
//			if (instance(appVar))
//				return ((AppList)appVar).get();
//			return null;
//		}
//		
//		public String toString(){
//			String out = "";
//			for (AppVar<? extends Object> el : obj )
//				out += el+"\n";
//			return out;
//		}
//	}
	
	public static class AppList extends AppVar<ArrayList<? extends AppVar<?>>>{

		public AppList(ArrayList<? extends AppVar<?>> arg) {
			super(arg);
		}

		@Override
		public String getType() {
			return "ArrayList<? extends AppVar<? extends Object>>";
		}

		@Override
		public boolean instance(AppVar<? extends Object> appVar) {
			return appVar instanceof AppList;
		}

		@Override
		public ArrayList<? extends AppVar<? extends Object>> convert(AppVar<? extends Object> appVar) {
			if (instance(appVar))
				return ((AppList)appVar).get();
			return null;
		}
		
		public String toString(){
			String out = "";
			for (AppVar<? extends Object> el : obj )
				out += el+"\n";
			return out;
		}
	}
		
	
	
}
