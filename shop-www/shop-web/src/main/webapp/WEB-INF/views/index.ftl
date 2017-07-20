<!DOCTYPE html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<link href="${ctx}/favicon.ico" rel="icon" type="image/x-icon" />
	<link href="${ctx}/slider/slider.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.tools.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.lazyload.js"></script>
	<script type="text/javascript" src="${ctx}/slider/slider.js"></script>
	<script type="text/javascript" src="${ctx}/js/common.js"></script>
	<style type="text/css">
		.header {
			margin-bottom: 0px;
		}
	</style>
<script type="text/javascript">
$().ready(function() {

	var $productCategoryMenuItem = $("#productCategoryMenu li");
	var $slider = $("#slider");
	var $newArticleTab = $("#newArticle ul.tab");
	var $hotGoodsImage = $("div.hotGoods img");
	
	$productCategoryMenuItem.hover(
		function() {
			$(this).children("div.menu").show();
		}, function() {
			$(this).children("div.menu").hide();
		}
	);
	
	$slider.nivoSlider({
		effect: "random",
		animSpeed: 1000,
		pauseTime: 6000,
		controlNav: true,
		keyboardNav: false,
		captionOpacity: 0.4
	});
	
	$newArticleTab.tabs("ul.tabContent", {
		tabs: "li",
		event: "mouseover"
	});
	
	$hotGoodsImage.lazyload({
		threshold: 100,
		effect: "fadeIn",
		skip_invisible: false
	});

});
</script>
</head>
<body>
<script type="text/javascript">
$().ready(function() {

	var $headerName = $("#headerName");
	var $headerLogin = $("#headerLogin");
	var $headerRegister = $("#headerRegister");
	var $headerLogout = $("#headerLogout");
	var $goodsSearchForm = $("#goodsSearchForm");
	var $keyword = $("#goodsSearchForm input");
	var defaultKeyword = "商品搜索";
	
	var username = getCookie("username");
	var nickname = getCookie("nickname");
	if ($.trim(nickname) != "") {
		$headerName.text(nickname).show();
		$headerLogout.show();
	} else if ($.trim(username) != "") {
		$headerName.text(username).show();
		$headerLogout.show();
	} else {
		$headerLogin.show();
		$headerRegister.show();
	}
	
	$keyword.focus(function() {
		if ($.trim($keyword.val()) == defaultKeyword) {
			$keyword.val("");
		}
	});
	
	$keyword.blur(function() {
		if ($.trim($keyword.val()) == "") {
			$keyword.val(defaultKeyword);
		}
	});
	
	$goodsSearchForm.submit(function() {
		if ($.trim($keyword.val()) == "" || $keyword.val() == defaultKeyword) {
			return false;
		}
	});

});
</script>
[#include "include/header.ftl"]

<div class="container index">
		<div class="row">
			<div class="span2">
					<div id="productCategoryMenu" class="productCategoryMenu">
						<ul>
							[@product_category_root_list count = 6]
							    [#list rootProductCategories as productCategory]
                                    <li>
                                        <div class="item">
                                            <div>
												[@product_category_children_list parentId=productCategory.id count=3 ]
													[#list productCategories as productCategory]
                                                        <a href="${ctx}/goods/list/productCategory.id">
                                                            <strong>${productCategory.name}</strong>
                                                        </a>
													[/#list]
												[/@product_category_children_list]
                                            </div>
                                            <div>
												[@brand_list categoryId=productCategory.id count=4 ]
													[#list brands as brand]
                                                        <a href="${ctx}/brand/list/brand.id">${brand.name}</a>
													[/#list]
												[/@brand_list]
                                            </div>
                                        </div>
                                        <div class="menu">
									[@product_category_children_list parentId=productCategory.id count=100 ]
										[#list productCategories as productCategory]
                                            <dl class="clearfix" [#if !productCategory_has_next]last[/#if]>
                                                <dt>
                                                    <a href="${ctx}/goods/list/${productCategory.id}">${productCategory.name}</a>
                                                </dt>
											[@product_category_children_list parentId=productCategory.id count=100 ]
												[#list productCategories as productCategory]
                                                <dd>
                                                    <a href="${ctx}/goods/list/${productCategory.id}">${productCategory.name}</a>[#if productCategory_has_next]|[/#if]
                                                </dd>
												[/#list]
											[/@product_category_children_list]
                                            </dl>
								[/#list]
							[/@product_category_children_list]

                                            <div class="auxiliary">
                                                <div>

                                                    <strong>推荐品牌</strong>
													[@brand_list categoryId=productCategory.id count=8 ]
														[#list brands as brand]
                                                            <a href="${ctx}/brand/list/brand.id">${brand.name}</a>
														[/#list]
													[/@brand_list]

                                                </div>
                                                <div>

													[@promotion_list categoryId=productCategory.id count=3 ]
														[#if promotions?has_content]
                                                            <strong>热门促销</strong>
															[#list promotions as promotion]
                                                                <a href="${ctx}/promotions/${promotion.id}" title="${promotion.name}">
                                                                    <img src="${promotion.image}" alt="${promotion.name}" />
                                                                </a>
															[/#list]
														[/#if]

													[/@promotion_list]


                                                </div>
                                            </div>
                                        </div>
                                    </li>

							    [/#list]
							[/@product_category_root_list]
						</ul>
					</div>
				</div>

			<div class="span10">
				[@ad_position positionId=1/]
			</div>
		</div>
		<div class="row">
			<div class="span9">
				[@ad_position positionId=2/]
			</div>
			<div class="span3">
						<div id="newArticle" class="newArticle">
								<ul class="tab">
									[@article_category_root_list count=2]
										[#list articleCategories as articleCategory]
											<li>
												<a href="${ctx}/articleCategory/${articleCategory.id}" target="_blank">${articleCategory.name}</a>
											</li>
										[/#list ]
									[/@article_category_root_list]

								</ul>
								[@article_category_root_list count=2]
									[#list articleCategories as articleCategory]
										<ul class="tabContent">
											[@article_list categoryId=articleCategory.id count=6]
												[#list articles as article]
                                                    <li>
                                                        <a href="${ctx}/article/${article.id}" title="${article.title}" target="_blank">${article.title}</a>
                                                    </li>
												[/#list ]
											[/@article_list]

										</ul>

									[/#list ]
								[/@article_category_root_list]

						</div>
			</div>
		</div>
		<div class="row">
			<div class="span12">
				<div class="middleAd">
					[@ad_position positionId=3/]
				</div>
			</div>
		</div>
			[#--热门商品展示--]
			[@ad_list positionId = 4]
				<!--[#assign adIterator = ads.iterator() /]-->
				[#assign ads = ads /]
			[/@ad_list]
			[@product_category_root_list count = 3]
				[#list rootProductCategories as productCategory]
					<div class="row">
						<div class="span12">
							<div class="hotGoods">
									<dl class="title1">
										<dt>
											<a href="${ctx}/goods/list/${productCategory.id}">${productCategory.name}</a>
										</dt>
										[@product_category_children_list parentId=productCategory.id count=100 ]
											[#list productCategories as productCategory]

												<dd>
													<a href="${ctx}/goods/list/${productCategory.id}">${productCategory.name}</a>
												</dd>
											[/#list]
										[/@product_category_children_list]

									</dl>
							[#--广告位--]
								[#if ads??]
                                    <div>
									[#--通过rootProductCategories集合的下标获取ad--]
										[#assign ad = ads[productCategory_index] /]
                                        <a href="${ctx}/ad/${ad.id}">
                                            <img src="${ad.path}" alt="${ad.title}" title="${ad.title}" />
                                        </a>
                                    </div>
								[/#if]
							[#--热门商品--]
								<ul>
									[@goods_list categoryId =productCategory.id  tagId=3 count=10 ]
										[#list goods as good]
											[#if good_index < 5]
                                                <li>
                                                    <a href="${ctx}/goods/content/${good.id}" title="${good.name}" target="_blank">
                                                        <div>
                                                            <span title="${good.name}">${good.name}</span>
                                                            <em title="${good.caption}">${good.caption}</em>
                                                        </div>
                                                        <strong>￥${good.price}</strong>
                                                        <img src="${ctx}/upload/image/blank.gif" data-original="${good.image}" />
                                                    </a>
                                                </li>
											[#else]
                                                <li class="low">
                                                    <a href="${ctx}/goods/detail/${good.id}" title="${good.name}" target="_blank">
                                                        <img src="${ctx}/upload/image/blank.gif" data-original="${good.image}" />
                                                        <span title="${good.caption}">${good.caption}</span>
                                                        <strong>￥${good.price}</strong>
                                                    </a>
                                                </li>
											[/#if]
										[/#list]
									[/@goods_list]
								</ul>
							</div>
						</div>
					</div>

				[/#list ]
			[/@product_category_root_list ]
		<div class="row">
			<div class="span12">
				[@ad_position positionId=5/]
			</div>
		</div>
		
		
		<div class="row">
			<div class="span12">
						<div class="hotBrand">
							<ul class="clearfix">
									[@brand_list count=10]
									    [#list brands as brand]
                                            <li>
                                                <a href="${ctx}/brand/${brand.id}" title="${brand.name}">
                                                    <img src="${brand.logo}" alt="${brand.name}" />
                                                </a>
                                            </li>
										[/#list ]
									[/@brand_list]

							</ul>
						</div>
			</div>
		</div>
		
		
	</div>
[#include "/include/footer.ftl"]
</body>
</html>