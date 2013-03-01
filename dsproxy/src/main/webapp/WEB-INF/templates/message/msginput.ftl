<#include "/include/header.ftl">
	<card id="${game_title}"><p>
	请输入私聊内容:<br/>
	<input name="msg" type="text" maxlength="30" emptyok="false" /><br/>
	<anchor>提交
		<@go href="/p?m_S/${pid}//${receiver}/" method="post"/>
			<postfield name="1" value="$msg" />
	     </go>
	</anchor>
	<@goback/>
	<@a href="/p?p_VO/${pid}/${receiver}"/>查看玩家</a><br/>
	<@gogame /><br/>
	</p>
	</card>
</wml>