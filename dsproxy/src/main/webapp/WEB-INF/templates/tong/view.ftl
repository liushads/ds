<#include "/include/header.ftl">
<card title="${game_title}"><p>
帮名: ${tong.name}<br/>
帮规: ${tong.description?if_exists}<br/>
帮主: <@a href="/p?p_VO/${pid}/${tong.ownerId}/"/>${tong.ownerName}</a><br/>
等级: ${tong.level}<br/>
人数: ${tong.amount}<br/>
加入帮会可以参与攻城战斗，共享帮会占领城市的收入，使用帮会仓库物品。<br/>
<#if tong_player?exists>
	<#-- 
	<#if tong_player.level==1>
	<@a href="/p?t_AA/${pid}/${tong.id}/7"/>申请同盟(7天)</a><br/>
	<@a href="/p?t_AA/${pid}/${tong.id}/30"/>申请同盟(30天)</a><br/>
	</#if>
	-->
	<#if tong_player.state==0>
	你正在申请帮会中，请等待审核。
	</#if>
<#else>
	<#if (tong.amount<tong_level.memberNum)>
	<@a href="/p?t_J/${pid}/${tong.id}/"/>申请入帮</a>
	<#else>
	帮会人数已满
	</#if>
</#if>
<br/>
<@goback/>
<@gogame/>
</p>
</card>
</wml>