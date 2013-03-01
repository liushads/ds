<#include "/include/header.ftl">

<card title="${game_title}">
<p>
请选择门派加入：<br/>
 <select name="sid">
<#list sects as sect>
<#if sect.id!=0>
              			<option value="${sect.id}">   ${sect.name}</option>
</#if>
</#list>
 </select><br/>
<anchor>加入门派
	<@go href="/p?se_I/${pid}/" method="post"/>
		<postfield name="1" value="$sid" />
	</go>
	</anchor>
</p></card>
</wml>