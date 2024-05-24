# 탑툰 클론 코딩

### 사용한 기술

Retrofit2, ViewPager2, Glide, WebView, Intent, ListAdapter, ViewBinding

### 학습 목표

1. Single Activity Architecture: 하나의 액티비티를 사용하고 나머지 화면을 모두 Fragment로 구성합니다.
2. JSON 파일을 활용한 데이터 관리 및 네트워크 통신 구현하기.
3. ListAdapter를 이용해 다양한 형태의 RecyclerView 구현하고 전체 레이아웃 따라 만들어보기.
4. ViewPager2 응용해보기

### 학습 내용

- 하나의 액티비티를 중심으로 FragmentContainer를 활용하여 Fragment를 교체하는 방식으로 구현했습니다.

- 인디케이터 구현을 위해 CustomView를 만들었고, ViewPager2를 사용하여 이미지 슬라이더를 구현했습니다.

- 이미지 슬라이더가 자동으로 넘어가도록 Runnable 객체를 사용했습니다.

- RecyclerView에서 적절한 이미지를 바인딩하기 위해 HashMap을 사용했습니다.

- 서버와의 통신을 모방하기 위해 직접 JSON 파일을 만들어 보았습니다.
  https://yundoun.github.io/toptoon_drawable/drawable.json

- JSON 파일 내의 식별자를 통해 웹툰 페이지로 이동하는 기능을 구현했습니다.
<pre><code>        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        String url = "https://toptoon.com/comic/ep_list/" + item.getSlug();
        intent.putExtra("URL", url);
        startActivity(intent);</code></pre>

- 다이얼로그를 구현하기 위해 DialogFragment를 사용했습니다.

- MainActivity에 drawerLayout을 포함시켜 구현했습니다.

### 성과

- RecyclerView, ListAdapter, ViewPager2 등에 대한 심화 학습
- 네트워크 통신 기술에 대한 심화 학습
- SAA 구조 이해하기

### 느낀점

- UI 로직과 네트워크 데이터 로직이 하나의 Fragment에 모두 포함되어 있어 코드의 가독성이 떨어진다는 것을 느꼈습니다. 이에 LiveData, Data Binding, 디자인 패턴(MVVM, MVP 등)을 추가로 학습할 필요가 있다고 생각합니다.

- 직접 서버를 구축하고 실시간 데이터를 처리해보고 싶다는 욕구가 생겼습니다.

- Fragment의 전환과 LifeCycle을 이해하는데 어려움을 겪어 백스택 관리와 뷰 관리에 대한 추가 학습이 필요하다고 느꼈습니다.

### 결과물

<image src="결과물/전체 메인 화면.gif"> <image src="결과물/메인메뉴_연재.jpg" width="400" height="800">

<br>

<image src="결과물/Dialog.jpg" width="400" height="800"> <image src="결과물/Drawer.jpg" width="400" height="800">

<br>

<image src="결과물/메인메뉴\_클릭.gif">

<br>

<image src="결과물/메인화면_아이템 클릭.gif">
