<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#assign ls = []>
<#macro itemlist mylist> 
	<#if (mylist?size >0)>
		<#list mylist?keys as k>
			<#if mylist[k].mission?exists>
			<br/><@a href="/p?m/${pid}/info/${k}/${mylist[k].mission.type}"/>
		 	${mylist[k].mission.name} <#if mylist[k].condition?exists>“${mylist[k].condition.targetName}”</#if>
		 	</a>
		 	</#if>
		</#list>
	<#else>
		<br/>没有任务
	</#if>  
</#macro>  
任务列表:

<#assign items=missions?default(ls)>

<#if missionType?exists>
<@itemlist mylist=items />
<#if missionType="playerType">
<#if missions?size &gt; 0>
<br/><@a href="/p?h_LM/${pid}/list/"/>击杀任务(${jisha})</a>
</#if>
</#if>
</#if>
<br/>
<#if td?exists>
<#--
<#if td ==1>
【温馨提示】主线任务每周四更新,请玩家关注最近更新
</#if>
-->
<#if td==2>
【温馨提示】每日任务可以到月牙村[新手村]衙门领取任务，任务需要斗神令，此任务也叫循环任务
</#if>
<#if td==3>
【温馨提示】押镖任务可以到月牙村[新手村]镇远镖局领取任务，任务需要押镖令
</#if>
</#if>
<br/>
<@a href="/p?m/${pid}/type"/>返回 </a>
<br/><@gogame />
</p>
</card>
</wml>