<#include "/include/header.ftl">
<card title="${game_title}"><p>
你的资产总额: ${goods_value+resell_gold}倒卖币<br/>
排名:XXXX+<br/>
现金总额:${resell_gold}倒卖币  <@a href="/p?rs_EGP/${pid}"/>兑换游戏币</a> 倒卖币商城<br/>
货价物值:${goods_value}倒卖币<br/>
物价刷新:${DateUtils.convertTime(space_time)}<@a href="/p?rs_M/${pid}/${opt_type}/${goods_type}/${page}"/>立即刷新</a>（今天还可以刷新${refresh_remain}/${refresh_limit}次）<br/>
<#assign r={"1":"买货","2":"卖货","3":"排行榜"}>
<#macro showType type target><#if type=target?string>${r[type]}<#else><@a href="/p?rs_M/${pid}/${type}/${goods_type}/${page}"/>${r[type]}</a></#if></#macro>
<#list r?keys as key><#if key_index!=0>|</#if><@showType key,opt_type/></#list><br/>
<#if opt_type = 1><!-- 买 -->
<#assign d={"1":"低挡","2":"中档","3":"高档","4":"奢侈品"}>
<#macro showType type target><#if type=target?string>${d[type]}<#else><@a href="/p?rs_M/${pid}/${opt_type}/${type}/${page}"/>${d[type]}</a></#if></#macro>
<#list d?keys as key><#if key_index!=0>|</#if><@showType key,goods_type/></#list><br/>
<#if page_objs?exists && (page_objs?size>0)>
<#list page_objs as obj>
${obj.name}:${obj.price}<#if obj.sign == 1>↑<#elseif obj.sign == -1>↓</#if><@a href="/p?rs_BGP/${pid}/${obj.id}"/>购买</a><br/>
</#list>
</#if>
<#if (page <total_page)><@a href="/p?rs_M/${pid}/${opt_type}/${goods_type}/${page+1}"/>下页</a>.</#if><#if (page>1)><@a href="/p?rs_M/${pid}/${opt_type}/${goods_type}/${page-1}"/>上页</a>.</#if><#if page!=1><@a href="/p?rs_M/${pid}/${opt_type}/${goods_type}/${1}"/>首页</a>.</#if><#if page!=total_page><@a href="/p?rs_M/${pid}/${opt_type}/${goods_type}/${total_page}"/>末页</a></#if><br/>
第${page}/${total_page}页   跳到第<input type="text" name="page_index" value="1"  maxlength="5" size="5" ></input>页
<anchor>GO
	<@go href="/p?rs_M/${pid}/" method="post"/>
    	<postfield name="1" value="${opt_type}" />
		<postfield name="2" value="${goods_type}" />
		<postfield name="3" value="$page_index" />
	</go>
</anchor>
<#elseif opt_type = 2><!-- 卖 -->
<#list page_objs as obj>
${obj.name}<#if obj.sign == 1>↑<#elseif obj.sign == -1>↓</#if><br/>
买价:${obj.buyPrice}<br/>
现价:${obj.price}<br/>
利润:${obj.price - obj.buyPrice}<br/>
<@a href="/p?rs_SGP/${pid}/${obj_index}/${obj.randomCode}"/>卖出</a><br/>
</#list>
<#elseif opt_type = 3><!-- 排行榜 -->
1.个个（以下也可以作为点击查看）<br/>
现金总额:<br/>
货物价值: <br/>
资产总额:<br/>
2.<br/>
下页
 跳到第5页GO<br/>
</#if>
<!-- 动态 -->
★最新动态★个个出售血饮10个，获得了200万倒卖币！<br/>
每小时登录1次斗神就有1000倒卖币增加！领取<br/>
<@goback/>
<@gogame/>
</p></card>
</wml>