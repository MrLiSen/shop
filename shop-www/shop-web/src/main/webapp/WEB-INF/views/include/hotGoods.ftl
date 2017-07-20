[@goods_list categoryId = categoryId tagId=3 count = 3]
    [#if goods?has_content]
    <div class="hotGoods">
        <dl>
            <dt>热销商品</dt>
            [#list goods as good]
                <dd>
                    <a href="${ctx}/goods/content/${good.id}">
                        <img src="${good.image}" alt="${good.name}" />
                        <span title="${good.name}">${good.name}111</span>
                    </a>
                    <strong>
                        ￥${good.price}
                        <del>￥${good.marketPrice}</del>
                    </strong>
                </dd>
            [/#list]

        </dl>
    </div>
    [/#if]
[/@goods_list]