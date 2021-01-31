
# Chapter 5 - 형식 맞추기

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [Chapter 5 - 형식 맞추기](#chapter-5-형식-맞추기)
  - [형식을 맞추는 목적](#형식을-맞추는-목적)
  - [적절한 행 길이를 유지하라](#적절한-행-길이를-유지하라)
    - [신문 기사처럼 작성하라](#신문-기사처럼-작성하라)
    - [개념은 빈 행으로 분리하라](#개념은-빈-행으로-분리하라)
    - [세로 밀집도](#세로-밀집도)
    - [수직 거리](#수직-거리)
    - [세로 순서](#세로-순서)
  - [가로 형식 맞추기](#가로-형식-맞추기)
    - [가로 공백과 밀집도](#가로-공백과-밀집도)
    - [가로 정렬](#가로-정렬)
    - [들여쓰기](#들여쓰기)
  - [팀 규칙](#팀-규칙)

<!-- /code_chunk_output -->


## 형식을 맞추는 목적
코드 형식은 중요하다! 너무나 중요하므로 융통성없이 맹목적으로 따르면 안된다.
코드형식은 의사소통의 일환이다. 의사소통은 전문 개발자의 일차적인 의무다.
오랜 시간이 지나 원래 코드 흔적을 찾아 볼 수 없을지라도, 처음 잡아놓은 구현 스타일과 가독성 수준은
유지보수 용이성과 확장성에 계속 영향을 미친다.


## 적절한 행 길이를 유지하라
유명한 프로젝트의 평균 파일의 세로 길이는 65줄이며 
최대 500줄을 대부분 넘기지 않으며 대부분 200줄 미만이다.

### 신문 기사처럼 작성하라
첫 문단은 전체 기사내용을 요약한다.
세세한 사실은 조금씩 드러난다. 날짜, 이름, 발언, 주장, 기타 세부사항이 나온다.
소스파일도 신문기사와 비슷하게 작성한다.
소스파일 첫 부분은 고차원 개념과 알고리즘을 명한다.
아래로 내려갈수록 의도를 세세하게 묘사한다

### 개념은 빈 행으로 분리하라
줄바꿈을 통해 개념을 분리할 수 있다.
```
// Bad
package fitnesse.wikitext.widgets;
import java.util.regex.*;
public class BoldWidget extends ParentWidget {
	public static final String REGEXP = "'''.+?'''";
	private static final Pattern pattern = Pattern.compile("'''(.+?)'''",
		Pattern.MULTILINE + Pattern.DOTALL);
	public BoldWidget(ParentWidget parent, String text) throws Exception {
		super(parent);
		Matcher match = pattern.matcher(text); match.find(); 
		addChildWidgets(match.group(1));}
	public String render() throws Exception { 
		StringBuffer html = new StringBuffer("<b>"); 		
		html.append(childHtml()).append("</b>"); 
		return html.toString();
	} 
}

// Good
package fitnesse.wikitext.widgets;

import java.util.regex.*;

public class BoldWidget extends ParentWidget {
	public static final String REGEXP = "'''.+?'''";
	private static final Pattern pattern = Pattern.compile("'''(.+?)'''", 
		Pattern.MULTILINE + Pattern.DOTALL
	);
	
	public BoldWidget(ParentWidget parent, String text) throws Exception { 
		super(parent);
		Matcher match = pattern.matcher(text);
		match.find();
		addChildWidgets(match.group(1)); 
	}
	
	public String render() throws Exception { 
		StringBuffer html = new StringBuffer("<b>"); 
		html.append(childHtml()).append("</b>"); 
		return html.toString();
	} 
}
```

### 세로 밀집도
줄바꿈이 개념을 분리한다면 세로 밀집도는 연관성을 의미한다.
서로 밀접한 코드 행은 세로로 가까이 놓여야한다.
```
// Bad
public class ReporterConfig {
    /**
    * 리포터 리스너의 클래스 이름
    */
	private String m_className;

    /**
    * 리포터 리스너의 속서
    */
	private List<Property> m_properties = new ArrayList<Property>();
	public void addProperty(Property property) { 
		m_properties.add(property);
	}

// Good
public class ReporterConfig {
	private String m_className;
	private List<Property> m_properties = new ArrayList<Property>();
	
	public void addProperty(Property property) { 
		m_properties.add(property);
	}
```

### 수직 거리
험수를 이해하려고 이 함수 저 함수 옮겨가거나, 상속관계를 줄줄이 거슬러 올라가는 경험은 좋지않은 경험이다.
서로 밀접한 개념은 세로로 가까이 둬야한다.(연관성)
타당한 근거가 없다면 서로 밀접한 개념은 한 파일에 속해야 한다 (`protected` 변수를 피해하하는 이유중 하나)
연관성이란 한 개념을 이해하는데 다른 개념이 중요한 정도이다.
연관성이 깊은 두 개념이 멀리 떨어져 있으면 코드를 읽는 사람이 여기저기 뒤지게 된다.

- 변수 선언   
    - 번수는 사용하는 위치에 최대한 가까이 선언한다.
- 인스턴수 변수 선언
    - 인스턴스 변수는 클래스 맨 처음에 선언한다.
    - 변수간 세로로 거리를 두지 않는다.
- 종속함수
    - 한 함수가 다른 함수를 호출한다면 두 함수는 세로로 가까이 배치한다.
    - 호출하는 함수를 호출되는 함수보다 먼저 배치한다.
- 개념적 유사성
    - 개념적인 친화도가 높을 수록 코드를 가까이 배치한다.
    - 명명법이 똑같거나 기본기능이 유사하고 간단한경우, 서로를 호출하는 관계는 부차적인 요인이다.
```
/// 변수선언 좋은 예시
public class TestSuite implements Test {
    static public Test createTest(Class<? extends TestCase> theClasses, String name){
        ...
    }

    public static Constructor<? extends TestCase> getTestConstructor(Class<? extends TestCase> theClass) throws NoSuchMethodException {
        ...
    }

    private static String exceptionToString(Throwable t) {
        ...
    }

    private String fName;

    private Vector<Test> fTests = new Vector<Test>(10);

    public TestSuite() {

    }

    public TestSuite(final Class<? extends TestCase> theClass) {
        ...
    }
}
```
```
/// 인스턴스 변수 선언의 좋은 예
/// 종속 함수 선언의 좋은 예
public class WikiPageResponder implements SecureResponder { 
	protected WikiPage page;
	protected PageData pageData;
	protected String pageTitle;
	protected Request request; 
	protected PageCrawler crawler;
	
	public Response makeResponse(FitNesseContext context, Request request) throws Exception {
		String pageName = getPageNameOrDefault(request, "FrontPage");
		loadPage(pageName, context); 
		if (page == null)
			return notFoundResponse(context, request); 
		else
			return makePageResponse(context); 
		}

	private String getPageNameOrDefault(Request request, String defaultPageName) {
		String pageName = request.getResource(); 
		if (StringUtil.isBlank(pageName))
			pageName = defaultPageName;

		return pageName; 
	}
	
	protected void loadPage(String resource, FitNesseContext context)
		throws Exception {
		WikiPagePath path = PathParser.parse(resource);
		crawler = context.root.getPageCrawler();
		crawler.setDeadEndStrategy(new VirtualEnabledPageCrawler()); 
		page = crawler.getPage(context.root, path);
		if (page != null)
			pageData = page.getData();
	}
	
	private Response notFoundResponse(FitNesseContext context, Request request)
		throws Exception {
		return new NotFoundResponder().makeResponse(context, request);
	}
	
	private SimpleResponse makePageResponse(FitNesseContext context)
		throws Exception {
		pageTitle = PathParser.render(crawler.getFullPath(page)); 
		String html = makeHtml(context);
		SimpleResponse response = new SimpleResponse(); 
		response.setMaxAge(0); 
		response.setContent(html);
		return response;
	} 
```

```
/// 개념적 유사성 예시
public class Assert {
	static public void assertTrue(String message, boolean condition) {
		if (!condition) 
			fail(message);
	}

	static public void assertTrue(boolean condition) { 
		assertTrue(null, condition);
	}

	static public void assertFalse(String message, boolean condition) { 
		assertTrue(message, !condition);
	}
	
	static public void assertFalse(boolean condition) { 
		assertFalse(null, condition);
	} 
```

### 세로 순서
일반적으로 함수 호출 종속성은 아래 방향으로 유지한다.
호출되는 함수를 호출하는 함수보다 나중에 배치한다.
자연스럽게 소스코드 모듈이 고차원에서 저차원으로 자연스럽게 내려간다.


## 가로 형식 맞추기
유명한 프로젝트의 가로형식은 평균 45자 근처다.
100~120자 정도의 가로길이 정도는 나쁘지않다.

### 가로 공백과 밀집도
가로로는 공백을 사용해 밀접한 개념과 느슨한 개념을 표현한다.
```
// 할당 연산자를 강조하기위해 앞 뒤에 공백을 준 케이스
// 함수이름과 인수 사이에 공백을 넣지않아 같은 개념이라는 것을 표시
// 함수를 호출하는 코드에서 괄호 안 인수는 공백으로 분리.
private void measureLine(String line) {
    lineCount++;
    int lineSize = line.length();
    totalChars += lineSize;
    lineWidthHistogram.addLine(lineSize, lineCount);
    recordWidestLine(lineSize);
}
```

### 가로 정렬
아래와 같은 정렬은 유용하지 못하다.
코드가 엉뚱한 부분을 강조해 진짜 의도가 가려진다.
선언부를 읽다보보면 변수 유형은 무시하고 변수 이름부터 읽게 된다.
```
public class FitNesseExpediter implements ResponseSender {
	private		Socket		  socket;
	private 	InputStream 	  input;
	private 	OutputStream 	  output;
	protected 	long		  requestParsingTimeLimit;
	private 	long		  requestProgress;
	private 	long		  requestParsingDeadline;
	private 	boolean		  hasError;
	
	public FitNesseExpediter(Socket         s,
                           FitNessContext context) throws Exception {
    this.context =            context;
    socket =                  s;
    requestParsingTimeLimit = 10000;
  }
```

### 들여쓰기
들여쓰기한 파일은 구조가 한 눈에 들어온다
때로는 간단한 if문 짧은 while문 등이 함수에서 들여쓰기 규칙을 무시하고픈 유혹이 생긴다.
하지만 들여쓰기로 범위를 제대호 표현한 코드를 선호한다.
```
// Bad
public class CommentWidget extends TextWidget 
{
    public static final String REGEXP = "^#[^\r\n]*(?:(?:\r\n)|\n\r)?";

    public CommentWidget(ParentWidget parent, String text){super(parent,text);}
    public String render() throws Exception {return ";}
}

/// Good
public class CommentWidget extends TextWidget {
    public static final String REGEXP = "^#[^\r\n]*(?:(?:\r\n)|\n\r)?";

    public CommentWidget(ParentWidget parent, String text){
        super(parent,text);
    }

    public String render() throws Exception {
        return ";
    }
}
```


## 팀 규칙
프로그래머라면 개인이 선호하는 규칙이 있지만, 팀에 속한다면 팀 규칙을 우선해야한다.