<#include "/include/header.ftl">
<card title="${game_title}"><p>
<img alt="" src="${img_server}/lovedaytree.gif" /><br/>
许愿树<br/>
携带你的许愿结为你们俩许个美好的心愿吧,你们的愿望大家都可以看见，每次许愿可以获得1次玩家的金钱祝福！发送祝福的玩家送出的金钱由许愿人获得，祝福玩家将获得系统送出的玫瑰奖励！<br/>
<#if wish?exists>
${wish}<br/>
</#if>
<input name="content" type="text" maxlength="15" emptyok="false" /><br/>
	<anchor>申请首页置顶展示(0.5金)
		<@go href="/p?n_love/${pid}/wish/" method="post"/>
			<postfield name="2" value="$content" />
			<postfield name="3" value="1" />
	     </go>
	</anchor>
<br/>
	<anchor>我有许愿结不用扣费
		<@go href="/p?n_love/${pid}/wish/" method="post"/>
			<postfield name="2" value="$content" />
			<postfield name="3" value="2" />
	     </go>
	</anchor>
	<br/>
<@a href="/p?n_love/${pid}/give/0/"/>送祝福</a><br/>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>