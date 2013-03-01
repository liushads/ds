<#include "/include/header.ftl">
<card title="${game_title}"><p>
禁言设置:<br/>
禁言时间：<br/>
<select name="jial_time">
<option value="3">3天</option>
<option value="7">一个星期</option>
<option value="30">一个月</option>
<option value="1825">永久</option>
</select>
<br/>
禁言类型：<br/>
<select name="jial_type">
<option value="0">公聊</option>
<option value="1">私聊</option>
<option value="5">公聊和私聊</option>
</select>
<br/>
禁言理由:<br/>
<input name="msg" type="text" maxlength="50" emptyok="true" /><br/>
<anchor>提交
	<@go href="/p?p_J/${pid}" method="post"/>
		<postfield name="1" value="add" />
		<postfield name="2" value="${jail_player}" />
		<postfield name="3" value="$jial_time" />
		<postfield name="4" value="$jial_type" />
		<postfield name="5" value="$msg" />
     </go>
</anchor>
<br/>
<@gogame/>
</p></card>
</wml>