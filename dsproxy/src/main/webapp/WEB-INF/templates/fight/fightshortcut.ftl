<#include "/include/header.ftl">
<#include "/include/sect.ftl">
<card title="${game_title}">
<p>
将物品设置为快捷键，即可在战斗中直接使用，请选择:<br/>
<#if objs?size=0><#-- steady 2010-06-28 增加没有绝学时引导 -->
提示:
<#if type == "jx">
<#--你当前还没有学习任何绝学,当你等级到达10级以后可以到<@outMainCityFacility player.sectId/>的【传功长老】处学习.<br/>-->
<#elseif type == "yp">
没有可以使用的药品,你可以到城里【药铺】购买.<br/>
<#elseif type == "aq">
没有可以使用的暗器.<br/>
</#if>
<#else>
<#if type == "yp">
	<#list objs as ls>
		<@a href="p?f_SSC/${pid}/yp/${return}/${ls.id}/"/>
		${ls.item.name}<#if (ls.amount > 0)>x${ls.amount}</#if>
		</a><br/>
	</#list>
<#elseif type == "aq">
	<#list objs as ls>
		<@a href="p?f_SSC/${pid}/aq/${return}/${ls.id}/"/>
		${ls.item.name}<#if (ls.amount > 0)>x${ls.amount}</#if>
		</a><br/>
	</#list>
<#elseif type == "jx">
	<#list objs as ls>
		<@a href="p?f_SSC/${pid}/jx/${return}/${ls.id}/"/>${ls.sectSkill.name}
		</a><br/>
	</#list>
</#if>

</#if>
<#if curr_facility?exists>
<@a href="/p?mv_W/${pid}/${curr_facility.id}/"/>返回战斗</a><br/>
</#if>
</p>
</card>
</wml>
