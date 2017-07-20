<!DOCTYPE html >
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/css/goods.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx}/js/jquery.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery.lazyload.js"></script>
    <script type="text/javascript" src="${ctx}/js/common.js"></script>
    <script type="text/javascript">
        $().ready(function() {

            var $headerCart = $("#headerCart");
            var $goodsForm = $("#goodsForm");
            var $orderType = $("#orderType");
            var $pageNumber = $("#pageNumber");
            var $pageSize = $("#pageSize");
            var $gridType = $("#gridType");
            var $listType = $("#listType");
            var $size = $("#layout a.size"); // #id 标签 class
            var $previousPage = $("#previousPage");
            var $nextPage = $("#nextPage");
            var $sort = $("#sort a, #sort li");
            var $orderMenu = $("#orderMenu");
            var $startPrice = $("#startPrice");
            var $endPrice = $("#endPrice");
            var $result = $("#result");
            var $productImage = $("#result img");
            var $addCart = $("#result a.addCart");

            var layoutType = getCookie("layoutType");
            if (layoutType == "listType") {
                $listType.addClass("currentList");
                $result.removeClass("grid").addClass("list");
            } else {
                $gridType.addClass("currentGrid");
                $result.removeClass("list").addClass("grid");
            }

            $gridType.click(function() {
                var $this = $(this);
                if (!$this.hasClass("currentGrid")) {
                    $this.addClass("currentGrid");
                    $listType.removeClass("currentList");
                    $result.removeClass("list").addClass("grid");
                    addCookie("layoutType", "gridType");
                }
                return false;
            });

            $listType.click(function() {
                var $this = $(this);
                if (!$this.hasClass("currentList")) {
                    $this.addClass("currentList");
                    $gridType.removeClass("currentGrid");
                    $result.removeClass("grid").addClass("list");
                    addCookie("layoutType", "listType");
                }
                return false;
            });

            $size.click(function() {
                var $this = $(this);
                $pageNumber.val(1);
                var pageSize = $this.attr("pageSize");
                $pageSize.val(pageSize);
                $goodsForm.submit();
                return false;
            });

            $previousPage.click(function() {
                $pageNumber.val(${paginator.prePage});
                $goodsForm.submit();
                return false;
            });

            $nextPage.click(function() {
                $pageNumber.val(${paginator.nextPage});
                $goodsForm.submit();
                return false;
            });

            $orderMenu.hover(
                    function() {
                        $(this).children("ul").show();
                    }, function() {
                        $(this).children("ul").hide();
                    }
            );

            $sort.click(function() {
                var $this = $(this);
                if ($this.hasClass("current")) {
                    $orderType.val("");
                } else {
                    var sort = $this.attr("orderType");
                    $("#orderType").val(sort);
                }
                $pageNumber.val(1);
                $goodsForm.submit();
                return false;
            });

            $startPrice.add($endPrice).focus(function() {
                $(this).siblings("button").show();
            });

            $startPrice.add($endPrice).keypress(function(event) {
                return (event.which >= 48 && event.which <= 57) || (event.which == 46 && $(this).val().indexOf(".") < 0) || event.which == 8 || event.which == 13;
            });

            $goodsForm.submit(function() {
                if ($orderType.val() == "" || $orderType.val() == "topDesc") {
                    $orderType.prop("disabled", true);
                }
                if ($pageNumber.val() == "" || $pageNumber.val() == "1") {
                    $pageNumber.prop("disabled", true);
                }
                /*
                if ($pageSize.val() == "" || $pageSize.val() == "20") {
                    $pageSize.prop("disabled", true);
                }*/
                if ($startPrice.val() == "" || !/^\d+(\.\d+)?$/.test($startPrice.val())) {
                    $startPrice.prop("disabled", true);
                }
                if ($endPrice.val() == "" || !/^\d+(\.\d+)?$/.test($endPrice.val())) {
                    $endPrice.prop("disabled", true);
                }
                if ($goodsForm.serializeArray().length < 1) {
                    location.href = location.pathname;
                    return false;
                }
            });

            $productImage.lazyload({
                threshold: 100,
                effect: "fadeIn"
            });

            // 加入购物车
            $addCart.click(function() {
                var $this = $(this);
                var productId = $this.attr("productId");
                $.ajax({
                    url: "/cart/add",
                    type: "POST",
                    data: {goodsId: productId, quantity: 1},
                    dataType: "json",
                    cache: false,
                    success: function(message) {
                        if (message.resultCode == 1) {
                            var $image = $this.closest("li").find("img");
                            var cartOffset = $headerCart.offset();
                            var imageOffset = $image.offset();
                            $image.clone().css({
                                width: 170,
                                height: 170,
                                position: "absolute",
                                "z-index": 20,
                                top: imageOffset.top,
                                left: imageOffset.left,
                                opacity: 0.8,
                                border: "1px solid #dddddd",
                                "background-color": "#eeeeee"
                            }).appendTo("body").animate({
                                width: 30,
                                height: 30,
                                top: cartOffset.top,
                                left: cartOffset.left,
                                opacity: 0.2
                            }, 1000, function() {
                                $(this).remove();
                            });
                            $.message('success', message.result);
                            $headerCart.find('em').html(parseInt($headerCart.find('em').html()) + 1);
                        } else {
                            $.message('error', message.resultMessage);
                        }
                    }
                });
                return false;
            });

            // 积分兑换
            $exchange.click(function() {
                var productId = $(this).attr("productId");
                $.ajax({
                    url: "/shopxx/order/check_exchange.jhtml",
                    type: "GET",
                    data: {productId: productId, quantity: 1},
                    dataType: "json",
                    cache: false,
                    success: function(message) {
                        if (message.type == "success") {
                            location.href = "/shopxx/order/checkout.jhtml?type=exchange&productId=" + productId + "&quantity=1";
                        } else {
                            $.message(message);
                        }
                    }
                });
                return false;
            });

            // 添加商品收藏
            $addFavorite.click(function() {
                var goodsId = $(this).attr("goodsId");
                $.ajax({
                    url: "/shopxx/member/favorite/add.jhtml",
                    type: "POST",
                    data: {goodsId: goodsId},
                    dataType: "json",
                    cache: false,
                    success: function(message) {
                        $.message(message);
                    }
                });
                return false;
            });

            // 对比栏
            var compareGoods = getCookie("compareGoods");
            var compareGoodsIds = compareGoods != null ? compareGoods.split(",") : [];
            if (compareGoodsIds.length > 0) {
                $.ajax({
                    url: "/shopxx/goods/compare_bar.jhtml",
                    type: "GET",
                    data: {goodsIds: compareGoodsIds},
                    dataType: "json",
                    cache: true,
                    success: function(data) {
                        $.each(data, function (i, item) {
                            var thumbnail = item.thumbnail != null ? item.thumbnail : "/shopxx/upload/image/default_thumbnail.jpg";
                            $compareBar.find("dt").after(
                                    '<dd> <input type="hidden" name="goodsIds" value="' + item.id + '" \/> <a href="' + escapeHtml(item.url) + '" target="_blank"> <img src="' + escapeHtml(thumbnail) + '" \/> <span title="' + escapeHtml(item.name) + '">' + escapeHtml(abbreviate(item.name, 50)) + '<\/span> <\/a> <strong>' + currency(item.price, true) + '<del>' + currency(item.marketPrice, true) + '<\/del><\/strong> <a href="javascript:;" class="remove" goodsId="' + item.id + '">[删除]<\/a> <\/dd>'					);
                        });
                        $compareBar.fadeIn();
                    }
                });

                $.each(compareGoodsIds, function(i, goodsId) {
                    $addCompare.filter("[goodsId='" + goodsId + "']").addClass("selected");
                });
            }

            // 移除对比
            $compareBar.on("click", "a.remove", function() {
                var $this = $(this);
                var goodsId = $this.attr("goodsId");
                $this.closest("dd").remove();
                for (var i = 0; i < compareGoodsIds.length; i ++) {
                    if (compareGoodsIds[i] == goodsId) {
                        compareGoodsIds.splice(i, 1);
                        break;
                    }
                }
                $addCompare.filter("[goodsId='" + goodsId + "']").removeClass("selected");
                if (compareGoodsIds.length == 0) {
                    $compareBar.fadeOut();
                    removeCookie("compareGoods");
                } else {
                    addCookie("compareGoods", compareGoodsIds.join(","));
                }
                return false;
            });

            $compareSubmit.click(function() {
                if (compareGoodsIds.length < 2) {
                    $.message("warn", "至少需要两个对比商品");
                    return false;
                }

                $compareForm.submit();
                return false;
            });

            // 清除对比
            $clearCompare.click(function() {
                $addCompare.removeClass("selected");
                $compareBar.fadeOut().find("dd:not(.action)").remove();
                compareGoodsIds = [];
                removeCookie("compareGoods");
                return false;
            });

            // 添加对比
            $addCompare.click(function() {
                var $this = $(this);
                var goodsId = $this.attr("goodsId");
                if ($.inArray(goodsId, compareGoodsIds) >= 0) {
                    return false;
                }
                if (compareGoodsIds.length >= 4) {
                    $.message("warn", "最多只允许添加4个对比商品");
                    return false;
                }
                $.ajax({
                    url: "/shopxx/goods/add_compare.jhtml",
                    type: "GET",
                    data: {goodsId: goodsId},
                    dataType: "json",
                    cache: false,
                    success: function(data) {
                        if (data.message.type == "success") {
                            $this.addClass("selected");
                            var thumbnail = data.thumbnail != null ? data.thumbnail : "/shopxx/upload/image/default_thumbnail.jpg";
                            $compareBar.show().find("dd.action").before(
                                    '<dd> <input type="hidden" name="goodsIds" value="' + data.id + '" \/> <a href="' + escapeHtml(data.url) + '" target="_blank"> <img src="' + escapeHtml(thumbnail) + '" \/> <span title="' + escapeHtml(data.name) + '">' + escapeHtml(abbreviate(data.name, 50)) + '<\/span> <\/a> <strong>' + currency(data.price, true) + '<del>' + currency(data.marketPrice, true) + '<\/del><\/strong> <a href="javascript:;" class="remove" goodsId="' + data.id + '">[删除]<\/a> <\/dd>'					);
                            compareGoodsIds.unshift(goodsId);
                            addCookie("compareGoods", compareGoodsIds.join(","));
                        } else {
                            $.message(data.message);
                        }
                    }
                });
                return false;
            });

            $.pageSkip = function(pageNumber) {
                $pageNumber.val(pageNumber);
                $goodsForm.submit();
                return false;
            }

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

<div class="container goodsList">
    <div id="compareBar" class="compareBar">
        <form action="/shopxx/goods/compare.jhtml" method="get">
            <dl>
                <dt>对比栏</dt>
                <dd class="action">
                    <a href="javascript:;" class="submit">对 比</a>
                    <a href="javascript:;" class="clear">清 空</a>
                </dd>
            </dl>
        </form>
    </div>
    <div class="row">
        <div class="span2">
            [#--分类--]
            [#include "include/hotProductCategory.ftl"/]
            [#--热门品牌--]
            [#include "include/hotBrand.ftl"/]
            [#--热销商品--]
            [#include "include/hotGoods.ftl"/]
            [#--促销商品--]
            [#include "include/hotPromotion.ftl"/]
        </div>
        <div class="span10">
            <div class="breadcrumb">
                <ul>
                    <li>
                        <a href="${ctx}/index">首页</a>
                    </li>
                    [#if goodsDto.keyword??]
                        <li>搜索 &quot;${goodsDto.keyword}&quot; 结果列表</li>
                    [/#if]

                </ul>
            </div>
            <form id="goodsForm" action="${ctx}/goods/search" method="get">
                <input type="hidden" id="keyword" name="keyword" value="${goodsDto.keyword}" />
                <input type="hidden" id="orderType" name="sort" value="${goodsDto.sort}" />
                <input type="hidden" id="pageNumber" name="page" value="${goodsDto.page}" />
                <input type="hidden" id="pageSize" name="pageSize" value="${goodsDto.pageSize}" />
                <div class="bar">
                    <div id="layout" class="layout">
                        <label>布局:</label>
                        <a href="javascript:;" id="gridType" class="gridType">
                            <span>&nbsp;</span>
                        </a>
                        <a href="javascript:;" id="listType" class="listType">
                            <span>&nbsp;</span>
                        </a>
                        <label>数量:</label>
                        <a href="javascript:;" class="size[#if goodsDto.pageSize == 20] current[/#if]" pageSize="20">
                            <span>20</span>
                        </a>
                        <a href="javascript:;" class="size[#if goodsDto.pageSize == 40] current[/#if]" pageSize="40">
                            <span>40</span>
                        </a>
                        <a href="javascript:;" class="size [#if goodsDto.pageSize == 80] current[/#if]" pageSize="80">
                            <span>80</span>
                        </a>
                    [#if paginator.totalPages > 1]
                        <span class="page">
								<label>共${paginator.totalCount}个商品 ${paginator.page}/${paginator.totalPages}</label>
                            [#if paginator.hasPrePage]
                                <a href="javascript:;" id="previousPage" class="previousPage">
                                        <span>上一页</span>
                                    </a>
                            [/#if]
                            [#if paginator.hasNextPage]
                                <a href="javascript:;" id="nextPage" class="nextPage">
                                        <span>下一页</span>
                                    </a>
                            [/#if]
							</span>
                    [/#if]
                    </div>
                    <div id="sort" class="sort">
                        <div id="orderMenu" class="orderMenu">
                            <span>${currentOrderType.sortValue}</span>
                            <ul>
                            [#list orderTypes as orderType ]
                                <li orderType="${orderType.sort}">${orderType.sortValue}</li>
                            [/#list]
                            [#--<li orderType="price.asc">价格升序 </li>--]
                            [#--<li orderType="price.desc">价格降序</li>--]
                            [#--<li orderType="sales.desc">销量降序</li>--]
                            [#--<li orderType="score.desc">评分降序</li>--]
                            [#--<li orderType="create_date.desc">日期降序</li>--]
                            </ul>
                        </div>
                        <a href="javascript:;" class="asc[#if goodsDto.sort == 'price.asc'] current[/#if]" orderType="price.asc">价格</a>
                        <a href="javascript:;" class="desc[#if goodsDto.sort == 'sales.desc'] current[/#if]" orderType="sales.desc">销量</a>
                        <a href="javascript:;" class="desc[#if goodsDto.sort == 'score.desc'] current[/#if]" orderType="score.desc">评分</a>
                        <input type="text" id="startPrice" name="startPrice" class="startPrice" value="${goodsDto.startPrice}" maxlength="16" title="价格过滤最低价格" onpaste="return false" />
                        <label>-</label>
                        <input type="text" id="endPrice" name="endPrice" class="endPrice" value="${goodsDto.endPrice}" maxlength="16" title="价格过滤最高价格" onpaste="return false" />
                        <button type="submit">确 定</button>
                    </div>
                </div>
                <div id="result" class="result grid clearfix">
                [#if results?has_content]
                    <ul>
                        [#list results as good]
                            <li>
                                <a href="${ctx}/goods/content/${good.id}">
                                    <img src="${ctx}/upload/image/blank.gif" data-original="${good.image}" />
                                    <div>
                                        <span title="${good.name}">${good.name}</span>
                                        <em title="${good.caption}">${good.caption}</em>
                                    </div>
                                </a>
                                <strong>
                                    ￥${good.price}
                                    <del>￥${good.marketPrice}</del>
                                </strong>
                                <div class="action">
                                    <a href="javascript:;" class="addCart" productId="${good.id}">加入购物车</a>
                                    <a href="javascript:;" class="addFavorite" title="收藏" goodsId="${good.id}">&nbsp;</a>
                                    <a href="javascript:;" class="addCompare" title="对比" goodsId="${good.id}">&nbsp;</a>
                                </div>
                            </li>
                        [/#list]
                    </ul>
                [#else]
                    没有查询到你想要的
                [/#if]
                </div>
            [#if paginator.totalPages > 1]
                <div class="pagination">
                    [#if paginator.isFirstPage()]
                        <span class="firstPage" >&nbsp;</span>
                    [#else]
                        <a href="javascript: $.pageSkip(1);" class="firstPage">&nbsp;</a>
                    [/#if]

                    [#if paginator.hasPrePage]
                        <a href="javascript: $.pageSkip(${paginator.prePage});" class="previousPage">&nbsp;</a>
                    [#else]
                        <span class="previousPage">&nbsp;</span>
                    [/#if]
                    [#list paginator.getSlider() as slider ]
                        [#if slider==paginator.page ]
                            <span class="currentPage">${slider}</span>
                        [#else]
                            <a href="javascript: $.pageSkip(${slider});">${slider}</a>
                        [/#if]

                    [/#list]
                    [#if paginator.hasNextPage]
                        <a href="javascript: $.pageSkip(${paginator.nextPage});" class="nextPage">&nbsp;</a>
                    [#else]
                        <span class="nextPage">&nbsp;</span>
                    [/#if]

                    [#if paginator.isLastPage()]
                        <span class="lastPage" >&nbsp;</span>
                    [#else]
                        <a href="javascript: $.pageSkip(${paginator.totalPages});" class="lastPage">&nbsp;</a>
                    [/#if]

                </div>
            [/#if]

            </form>
        </div>
    </div>
</div>
<[#include "include/footer.ftl"/]
</body>
</html>