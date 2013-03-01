<?xml version='1.0' encoding='UTF-8'?>
<!DOCTYPE wml PUBLIC '-//WAPFORUM//DTD WML 1.1//EN' 'http://www.wapforum.org/DTD/wml_1.1.xml'>
<wml>
<head>
  <meta http-equiv="Content-type" content="text/vnd.wap.wml; charset=utf-8"/>
  <meta http-equiv='Cache-Control' content='max-age=0' forua='true'/>
</head>
<#include "/include/config.ftl">
<#setting number_format="0.##"/>
<#assign sect_name={"0":"无","1":"毒龙教","2":"金刚堡","3":"暗河宫","4":"百花门","5":"丐帮"}/>
<#assign sex_name={"0":"女","1":"男"}/>

<#assign userKey = ""/>
<#if player?exists && player.passwdBook?exists >
<#assign userKey = player.passwdBook>
</#if>

<#macro encodeUrl href, passwd, sysKey>
<#local base = ""/>
<#local param = ""/>
<#list href?split("?") as x>
<#if x_index = 0><#local base = x/></#if>
<#if x_index = 1><#local param = x/></#if>
</#list>
<#if base?starts_with("/")>
 <#local base = gameBaseUrl[0..gameBaseUrl?length-2]+base/>
</#if>
<#if !param?ends_with("/")><#local param = param+"/"/></#if>
<#local newUrl = base + "?" + MD5.encodeAndRandom(param, passwd, sysKey) + "-" + param/>
${newUrl}</#macro>
<#macro encodeUrlParam href>
<#local param = ""/>
<#list href?split("?") as x>
<#if x_index = 1><#local param = x/></#if>
</#list>
<#if !param?ends_with("/")><#local param = param+"/"/></#if>
<#local newUrl = MD5.encodeAndRandom(param, userKey, Md5Key) + "-"+ param/>
${newUrl}</#macro>

<#macro a href><a href="<@encodeUrl href, userKey, Md5Key/>"></#macro>
<#macro go href method><go href="<@encodeUrl href, userKey, Md5Key/>" method="${method}"></#macro>
<#macro ag href><a href="<@encodeUrl href, "", Md5GlobalKey/>"></#macro>

<#macro sign param>
${MD5.encodeAndRandom(param, userKey, Md5Key)}</#macro>

<#macro gogame>
<#if facility?exists> 
<@a href="/p?mv_W/${pid}/${facility.id}"/>返回游戏</a><br/>
<#else>
<@a href="/p?s_G/${pid}/"/>继续</a><br/>
</#if>
</#macro>

<#macro ad>
</#macro>

<#macro ad1>
</#macro>

<#macro ad2>
</#macro>

<#macro goback>
<anchor>
	<prev/>返回<br/>
</anchor>
</#macro>

<#macro gofacility>
<#if curr_facility?exists>
<@a href="/p?mv_W/${pid}/${curr_facility.id}/"/>返回${curr_facility.name}</a><br/>
</#if>
</#macro>

<#macro gogame>
<#if curr_facility?exists>
<@a href="/p?mv_W/${pid}/${curr_facility.id}"/>返回游戏</a><br/>
</#if>
</#macro>

<#macro goStatus>
<@a href="/p?p_VS/${pid}/"/>返回状态</a>
</#macro>

<#macro gomall>
<@a href="/p?ma_I/${pid}/1"/>返回商城</a><br/>
</#macro>

<#macro gobuy>
<@a href="/p?ma_I/${pid}/1/"/>商城购买</a><br/>
</#macro>

<#macro gopay>
<@a href="/p?pa_I/${pid}/1/"/>充值</a><br/>
</#macro>

<#macro refresh>
<#if (label_local_url?exists)>
<a href="${label_local_url}">刷新</a><br/>
</#if>
</#macro>

<#macro showMessage message>
	<#t>
	<#if (message.typeId < 10)>
	   <#if message.fromPlayerName?exists>
	      <#if message.fromPlayerId == player.id>
	            <@nick message.fromPlayerName/>
	      <#else>
	         <@a href="/p?m_I/${pid}/${message.fromPlayerId}"/><@nick message.fromPlayerName/>(回)</a>
	      </#if>
	   <#else>
	      <#if message.typeId == 0>斗神快报<#else>秘书小Q</#if>
	   </#if>
	   :
	</#if> 
	<#t>
	
	<#if (message.typeId == 0)>
	
     	<#if (message.fromPlayerId?exists && message.fromPlayerId ==0)>
	      <#if message.content?index_of("新手神兵★刀") != -1>
	         <#assign x =message.content?index_of("新手神兵★刀")/>
	         ${message.content?substring(0,x)}
	         <#assign ax =message.content?index_of("(")/>
	         <#assign bx =message.content?index_of(")")/>
	         <@a href="/p?i_I/${pid}/${message.content?substring(ax+1,bx)}/${message.content?substring(bx+14)}"/>
	         ${message.content?substring(x,x+6)}</a>
	         ${message.content?substring(bx+1,bx+11)}<a href="/tishi.wml">${message.content?substring(bx+11,bx+14)}</a>
	      <#elseif message.content?index_of("新手神兵★扇") != -1>
	         <#assign x =message.content?index_of("新手神兵★扇")/>
	         ${message.content?substring(0,x)}
	         <#assign ax =message.content?index_of("(")/>
	         <#assign bx =message.content?index_of(")")/>
	         <@a href="/p?i_I/${pid}/${message.content?substring(ax+1,bx)}/${message.content?substring(bx+14)}"/>
	         ${message.content?substring(x,x+6)}</a>
	         ${message.content?substring(bx+1,bx+11)}<a href="/tishi.wml">${message.content?substring(bx+11,bx+14)}</a>
	      <#elseif message.content?index_of("新手神兵★棍") != -1>
	         <#assign x =message.content?index_of("新手神兵★棍")/>
	         ${message.content?substring(0,x)}
	         <#assign ax =message.content?index_of("(")/>
	         <#assign bx =message.content?index_of(")")/>
	         <@a href="/p?i_I/${pid}/${message.content?substring(ax+1,bx)}/${message.content?substring(bx+14)}"/>
	         ${message.content?substring(x,x+6)}</a>
	         ${message.content?substring(bx+1,bx+11)}<a href="/tishi.wml">${message.content?substring(bx+11,bx+14)}</a>
	      <#elseif message.content?index_of("新手神兵★刺") != -1>
	         <#assign x =message.content?index_of("新手神兵★刺")/>
	         ${message.content?substring(0,x)}
	         <#assign ax =message.content?index_of("(")/>
	         <#assign bx =message.content?index_of(")")/>
	         <@a href="/p?i_I/${pid}/${message.content?substring(ax+1,bx)}/${message.content?substring(bx+14)}"/>
	         ${message.content?substring(x,x+6)}</a>
	         ${message.content?substring(bx+1,bx+11)}<a href="/tishi.wml">${message.content?substring(bx+11,bx+14)}</a>
	      <#elseif message.content?index_of("新手神兵★剑") != -1>
	         <#assign x =message.content?index_of("新手神兵★剑")/>
	         ${message.content?substring(0,x)}
	         <#assign ax =message.content?index_of("(")/>
	         <#assign bx =message.content?index_of(")")/>
	         <@a href="/p?i_I/${pid}/${message.content?substring(ax+1,bx)}/${message.content?substring(bx+14)}"/>
	         ${message.content?substring(x,x+6)}</a>
	         ${message.content?substring(bx+1,bx+11)}<a href="/tishi.wml">${message.content?substring(bx+11,bx+14)}</a>
	      <#else>
	          ${message.content?html}
	      </#if>
	
	   <#else>
	      ${message.content?html}
	   </#if>  
	<#else>
	  ${message.content}
	</#if>
	<#if (message.typeId == 0)
	   >(${message.createTime?string("HH:mm")})
	<#else>
	  <#if message.createTime?exists>
	    (${message.convertCreateTime()})
	  </#if>
	</#if>
	<#if message.actionList?exists>
		<#list message.actionList as action>
			<#t><@a href="/p?${action.url}/${pid}/"/>${action.name}</a>
		</#list>		
	</#if><br/>
</#macro>

<#macro showSpecialNpcImg npcId >
	<#local img = 0/>
	<#switch npcId>
 	<#case "10"><#local img = 1/><#break>
 	<#case "13"><#local img = 1/><#break> 
 	<#case "38"><#local img = 1/><#break>
 	<#case "57"><#local img = 1/><#break>   
 	<#case "58"><#local img = 1/><#break> 
 	<#case "62"><#local img = 1/><#break> 
 	<#case "63"><#local img = 1/><#break> 
 	<#case "64"><#local img = 1/><#break> 
 	<#case "65"><#local img = 1/><#break> 	 
 	<#case "75"><#local img = 1/><#break>
 	<#case "76"><#local img = 1/><#break>
 	<#case "77"><#local img = 1/><#break>
 	<#case "78"><#local img = 1/><#break>
 	<#case "79"><#local img = 1/><#break>
 	<#case "117"><#local img = 1/><#break>   
 	<#case "298"><#local img = 1/><#break>    
    <#default>
    <#local img = 0/>
	</#switch><#if (img > 0)><img alt="" src="${img_server}/fun_npc.gif" /></#if></#macro>
<#macro nick nick>${nick?html}</#macro>

<#macro speedUp>
	<@a href="/p?d_SU/${pid}/0/"/>加速</a><br/>
	<@a href="/p?d_SU/${pid}/1/"/>直接完成</a>
</#macro>
