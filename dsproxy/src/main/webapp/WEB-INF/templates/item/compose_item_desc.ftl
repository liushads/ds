<#include "/include/header.ftl">
<#include "/include/head_card.ftl">
<#if label_desc?exists>
${label_desc}<br/>
</#if>
<#if label_cv?exists>
	<#list label_cv.compose as co>
		<@a href="/p?i_II/${pid}/${co.itemId}/"/>${co.name}</a>(${co.amount}/${co.needNum})<br/>
	</#list>
	
	<#if (label_cv.gold > 0)>
	人民币：${label_cv.gold}<br/>
	<#else>
	游戏币：${label_cv.copper}<br/>
	</#if>
	<@a href="/p?i_CI/${pid}/${label_suit_id}/ok/"/>合成</a><br/>
</#if>
<#if label_less?exists>
合成失败，还缺少以下材料<br/>
	<#if label_less.compose?exists>
		<#list label_less.compose as co>
			<@a href="/p?i_II/${pid}/${co.itemId}/"/>${co.name}</a>(${co.amount})<br/>
		</#list>
	</#if>
	<#if (label_less.gold > 0)>
	人民币：${label_less.gold}<br/>
	<#elseif (label_less.copper > 0)>
	游戏币：${label_less.copper}<br/>
	</#if>
</#if>
<@gogame/>
<#include "/include/foot_card.ftl">