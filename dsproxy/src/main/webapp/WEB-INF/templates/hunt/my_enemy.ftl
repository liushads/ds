<#include "/include/header.ftl">
<card title="${game_title}"><p>
仇家列表：<br/>
<#if label_items?exists && label_items?size &gt; 0 >
	<#list label_items as item> 
	 	<@a href="/p?h/${pid}/create/57/${item.enemyId}"/>通缉</a>&nbsp;${item.enemyName}(被他杀了${item.killedTimes}次)&nbsp;<@a href="/p?h_SM/${pid}/delete/${item.enemyId}"/>删除</a><br/>
	</#list>
<#else>
目前没有仇家	<br/>
</#if>
 <@a href="/p?h_SM/${pid}/reward/"/>我的通缉</a><br/>
<@goback/>
<@gofacility/>
</p>
</card>
</wml>