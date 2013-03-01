<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
${label_npc.name}：仇恨似海洋永难忘……选择你通缉的仇家：
<#if label_items?exists && label_items?size &gt; 0 >
	<#list label_items as item> 
		<br/><@a href="/p?h/${pid}/create/${label_npc.id}/${item.enemyId}"/>
	 	${item.enemyName} (被他杀了${item.killedTimes}次)
	 	</a>
	</#list>
<#else>
<br/>目前没有仇家	
</#if>
<br/><@a href="/p?p_T/${pid}/${label_npc.id}"/>返回</a>
<br/><@gofacility/>
</p>
</card>
</wml>
