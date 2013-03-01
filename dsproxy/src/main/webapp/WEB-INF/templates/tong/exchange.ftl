<#include "/include/header.ftl">
<card title="${game_title}">
	<p>
	<#if desc?exists>
	${desc}<br/>
	</#if>
	提示：帮会占领一个以上城市后才能进行分钱。<br/>
	<#assign money = tong.copper-tong_level.fixedAssets />
	帮会福利为：<#if (money>0)>${money}<#else>0</#if><br/>
	你的帮贡为：${tong_player.mark}<br/>
	<#if (money>0) >
		<#if (total_mark>0)>
		你可以使用1点帮贡换：${money/total_mark}<br/>
		<@a href="/p?t_Ex/${pid}/1"/>使用1点帮贡</a><br/>
		<#if (tong_player.mark>=10)>
		<@a href="/p?t_Ex/${pid}/10"/>使用10点帮贡</a><br/>
		</#if>
		<#if (tong_player.mark>=100)>
		<@a href="/p?t_Ex/${pid}/100"/>使用100点帮贡</a><br/>
		</#if>
		<@a href="/p?t_Ex/${pid}/-1"/>全部换取</a><br/>
		<#else>
		没有努力就没有收获，你还没有为帮会做出贡献，继续加油吧！<br/>
		</#if>
	<#else>
		帮会没有福利可以分，继续努力吧....<br/>
	</#if>
	<@a href="/p?t_M/${pid}/"/>返回帮会</a><br/>
	<@gogame/>	   
	</p>
</card>
</wml>