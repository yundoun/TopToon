# 탑툰 클론 코딩

### 사용한 기술

Retrofit2, ViewPager2, Glide, WebView, Intent, ListAdapter, ViewBinding

### 학습 목표

1. Single Activity Architecture : 하나의 액티비티를 사용하고 나머지 화면을 모두 Fragment로 구성
2. 데이터 관리를 위한 JSON 파일을 만들고, 네트워크 통신하기
3. ListAdapter를 사용해서 여러 형태의 RecyclerView 만들어보기 / 전체적인 레이아웃 따라 만들어보기
4. ViewPager2 응용해보기

### 학습 내용

- 하나의 액티비티를 기준으로 FragmentContainer를 이용해서 Fragment를 Replace 하게끔 구현하였습니다.

- 인디케이터를 구현하기 위해 CustonView를 생성하였고, ViewPager2를 사용해서 이미지 슬라이더를 구현하였습니다.

- ViewPager2 슬라이더를 자동으로 넘어가게 구현하기 위해 Runnable 객체를 사용했습니다.

- RecyclerView 태그에 맞는 이미지를 바인딩하기 위해 HashMap을 사용했습니다.

- 서버와의 통신을 모방하고, 데이터 관리를 위해 JSON 파일을 직접 만들어보았습니다.
  https://yundoun.github.io/toptoon_drawable/drawable.json

- JSON 파일에 식별자를 포함시켜 식별자를 통해 해당 웹툰으로 이동하게 구현하였습니다.
<pre><code>        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        String url = "https://toptoon.com/comic/ep_list/" + item.getSlug();
        intent.putExtra("URL", url);
        startActivity(intent);</code></pre>

- Dialog를 구현하기 위해 DialogFragment를 사용했습니다.

- drawerLayout을 MainActivity에 include 하여 구현하였습니다.

### 성과

- RecyclerView, ListAdapter, ViewPager2 심화 학습
- 네트워크 통신 심화 학습
- SAA 구조 이해하기

### 느낀점

- UI 로직과 네트워크 데이터 로직이 하나의 Fragment에 모두 들어가있어서 코드의 가독성이 떨어진다
  ⇒ LiveData, Data Binding, 디자인 패턴 적용( MVVM, MVP,,,) 등 추가적인 개념 학습 필요하다고 생각됩니다.

- 서버를 직접 개설하고 실시간 데이터를 다루어 보고 싶은 욕심이 생겼습니다.
- Fragment 전환과 LifeCycle 이 머리 속으로 잘 그려지지 않아서 백스택 관리와 뷰 관리가 어려워서 추가 학습이 필요할 것 같습니다.

### 결과물

<image src="결과물/전체 메인 화면.gif"> <image src="결과물/메인메뉴_연재.jpg" width="400" height="800">

<br>

<image src="결과물/Dialog.jpg" width="400" height="800"> <image src="결과물/Drawer.jpg" width="400" height="800">

<br>

<image src="결과물/메인메뉴\_클릭.gif">

<br>

<image src="결과물/메인화면_아이템 클릭.gif">
