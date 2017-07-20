[#--热门品牌--]
[@brand_list categoryId=categoryId count=6]
[#if brands?has_content]
<div class="hotBrand clearfix">
    <dl>
        <dt>热门品牌</dt>

        [#list brands as brand]
            <dd [#if brand_index%2==0]class="even" [/#if]>
                <a href="${ctx}/brand/content/${brand.id}" title="${brand.name}">
                    <img src="${brand.logo}" alt="${brand.name}" />
                    <span>${brand.name}</span>
                </a>
            </dd>
        [/#list]

    </dl>
</div>
[/#if]

[/@brand_list]