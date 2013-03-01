<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<#assign d={"1":"热卖","2":"宝石","3":"材料","4":"宠物","5":"药品"}>
<#macro showType type target>
	<#if type=target>
		${d[type]}
	<#else>
		<@a href="/p?ma_I/${pid}/${type}"/>${d[type]}</a>
	</#if>
</#macro>

<card title="${game_title}"><p>
${game_title}商城<br/>
<@ad2/>
你现有: <@showPlayerMoney player.advGold,player.gold,-1/>积分<#if player.rewardPoints?exists && player.rewardPoints &gt; 0>${player.rewardPoints}<#else>0</#if><br/>
<@a href="/p?pa_I/${pid}/1/"/>充值</a>  购买银矿石可以得到银<br/>
【<#list d?keys as key>
	<@showType key,type/> <#if key_has_next>|</#if>  
</#list><#-- <@a href="/p?ma_I/${pid}/"/>积分兑换</a> -->
】
<br/>
<#if page_objs?exists && (page_objs?size>0)>
	<#list page_objs as pkg>
		<@a href="/p?ma_BGN/${pid}/${pkg.id}"/>${pkg.name}(${pkg.gold/100}水晶)</a><br/>
	</#list>
  	<#if (page>0)><@a href="/p?ma_I/${pid}/${type}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="/p?ma_I/${pid}/${type}/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>	
</#if>
今日银价：<br/>
暂时无银兑换,<@a href="/p?ma_BGN/${pid}/46"/>购买银矿石</a><br/>
<@a href="/p?p_m/${pid}/b/"/>购买会员</a><br/>
<@a href="/p?p_m/${pid}/mr/"/>查看会员福利</a><br/>
<@a href="/p?i_PE/${pid}/l/0/"/>积分兑换</a><br/>
<#--  1金兑换${today_silver}银-->
<#--  <@a href="/p?ma_BSN/${pid}/"/>兑换银币</a><br/>-->
<@gogame/>
</p></card>
</wml> 