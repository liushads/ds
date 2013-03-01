<#include "/include/header.ftl">
<card title="${game_title}"><p>
	<#if input_err?exists>
	${input_err}<br/><br/>
	</#if>
	<#if playerFriend?exists>
	查找成功:<br/>
	<@a href="/p?p_VO/${pid}/${playerFriend.friendId}/"/>${playerFriend.name}</a>(<#if playerFriend.level?exists>${playerFriend.level}级</#if>${playerFriend.location})<@a href="/p?p_DFC/${pid}/${playerFriend.friendId}"/>删除</a><br/>
	<#else>
	好友查找,请输入好友ID:<br/>
	<input name="fid" type="text" maxlength="11" emptyok="false" /><br/>
	<anchor>查找
		<@go href="/p?p_FF/${pid}/" method="post"/>
			<postfield name="1" value="$fid" />
	     </go>
	</anchor><br/>
	</#if>
	
	<@a href="/p?p_LF/${pid}/0"/>好友列表</a><br/>
	<@gogame /><br/>
	</p>
	</card>
</wml>