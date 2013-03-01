<#include "/include/header.ftl">
<card title="${game_title}"><p>
会员用户<br/>
<#if playerMember?exists>
<#if isNotify?exists && isNotify>
会员服务即将到期或已过期，请续费<@a href="/p?p_m/${pid}/b/"/>续费</a><br/>
</#if>
身份：【${playerMember.memberLevelStr} 会员用户<br/>
<@a href="/p?p_m/${pid}/m_lv/"/>会员成长值介绍</a><br/>
<@a href="/p?p_m/${pid}/m_lp/"/>会员等级福利介绍</a><br/>
<@a href="/p?i_PE/${pid}/l/0/"/>积分兑换</a><br/>
</#if>
<@goback/>
<@gogame/>
</p></card>
</wml>