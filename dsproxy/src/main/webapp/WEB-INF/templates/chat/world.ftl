<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if player?exists && (player.cityFacilityId == 0)>
<@a href="/p?mv_G/${pid}/"/>继续游戏</a>
</#if>
<#if chat_status?exists>
 各位玩家:<br/>
为了营造健康游戏环境，坚决抵制色情低级粗俗及政治的言论信息，让我们共同自律与监督！有违规行为的用户将被禁言或直接删除帐号，严重者直接上报公安机关！<br/>
斗神是我家，绿色靠大家！<br/>
聊天室及论坛近期将实行宵禁，开放时间:早上8点至晚上23点。<br/>
<@goback/>
<#else>
聊天频道-<@a href="/p?ca_L/${pid}/0/${msg_type}"/>刷新</a><br/>
<#macro title type>
	<#if type == "1">
		[私聊]
	<#elseif type == "2">
		[帮会]
	<#elseif type == "3">
		[队伍]
	<#elseif type == "4">
		[门派]
	<#else>
		[公共]
	</#if>			
</#macro>
<#if msg_type?number!=0><@a href="/p?ca_L/${pid}/0/0"/>公共.</a><#else>公共.</#if> 
<#if msg_type?number!=1><@a href="/p?ca_L/${pid}/0/1"/>私聊.</a><#else>私聊.</#if>  
<#if msg_type?number!=2><@a href="/p?ca_L/${pid}/0/2"/>帮会.</a><#else>帮会.</#if>
<#if msg_type?number!=3><@a href="/p?ca_L/${pid}/0/3"/>队伍.</a><#else>队伍.</#if>
<#if msg_type?number!=4><@a href="/p?ca_L/${pid}/0/4"/>门派</a><#else>门派</#if>
<br/>
<#if msg_type?number!=0><#else><@a href="/p?p_KF/${pid}/g/"/>游戏解答</a>     <a href="/wenxintishi.wml">温馨提示</a><br/></#if>

<#if (page_objs?size>0)>
	<#list page_objs as ls> 
		<@title msg_type /><@showMessage ls />
	</#list>
	<#if (page_objs?size==10)>
		<@a href="/p?ca_L/${pid}/${page+1}/${msg_type}/"/>下页</a>.
	</#if>
	<#if (page>0)>
		<@a href="/p?ca_L/${pid}/${page-1}/${msg_type}/"/>上页</a>
	</#if><br/>
<#else>
	还没有消息！<br/>
</#if>
	<#-- #if (player.level<2) || (player.status==1) -->
	<#if msg_type?number == 1>
	<#elseif msg_type?number == 3 && player.teamId == 0>
	没有加入任何队伍！<br/>
	<#else>
	请输入聊天内容:<br/>
	<input name="msg" type="text" maxlength="30" emptyok="false" /><br/>
	<anchor>确定
		<@go href="/p?ca_S/${pid}//${msg_type}/" method="post"/>
			<postfield name="1" value="$msg" />
	     </go>
	</anchor>
	<br/>
	</#if>
</#if>
<@goback />
<#if player?exists && (player.cityFacilityId == 0)>
<@a href="/p?mv_G/${pid}/"/>继续游戏</a>
</#if>
<@gogame />
</p>
</card>
</wml>

