<#include "/include/header.ftl">
<card title="${game_title}">
	<p>
	
	帮会管理<br/><br/>
	<@a href="/p?t_MAu/${pid}/"/>入帮管理</a><br/>
	<@a href="/p?t_MAs/${pid}/"/>仓库管理</a><br/>
	<@a href="/p?t_MP/${pid}/"/>成员管理</a><br/>
	<#-- <@a href="/p?t_MAl/${pid}/1/"/>同盟管理</a><br/> -->
	<@a href="/p?t_MU/${pid}/"/>升级管理</a><br/>
	<@a href="/p?t_MN/${pid}/"/>公告管理</a><br/>
	<br/>
	<@a href="/p?t_M/${pid}/"/>返回帮会</a><br/>
	<@gogame/>	   
	</p>
</card>
</wml>