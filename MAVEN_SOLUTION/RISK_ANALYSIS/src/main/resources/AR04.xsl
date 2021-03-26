<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml"/>

    <xsl:template match="/">
        <xsl:text disable-output-escaping='yes'>&lt;!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
            "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"&gt;</xsl:text>
        <html>
            <body>
                <h1 id="code">
                    Caso
                    <xsl:value-of select="case/code"/>
                </h1>
                <xsl:for-each select="case/insurances/insuranceobject">
                    <div id="insuranceobject">
                        <h2>
                            <xsl:value-of select="objectname"/>
                        </h2>
                        <h4>
                            <xsl:value-of select="location"/>
                        </h4>
                        <h4>Details</h4>
                        <ul>
                    
                            <xsl:for-each select="details/detail">
                                <li id="detail">
                                    <xsl:value-of select="."/>
                                </li>
                            </xsl:for-each>
                        </ul> 
                        <h4 id="riskIndex">
                            <xsl:value-of select="riskindex"/>
                        </h4>
                    
                        <br/>
                    
                        <h4>Classification</h4>
                        <table id="tabelaClass" border="1">
                         
                            <xsl:for-each select="classificationtable/coverageclass">
                            
                                <tr>
                                    <td rowspan="2">
                                        
                                    </td>
                                    <xsl:for-each select="surroundings/surroundingtype">
                                        <th id="surrounding" colspan="{count(riskfactors/riskfactor)}" scope="colgroup">
                                            <xsl:value-of select="sname"/>
                                        </th>
                                    </xsl:for-each>
                                </tr>
                            
                                <tr>
                                    <xsl:for-each select="surroundings/surroundingtype/riskfactors/riskfactor">
                                        <th id="metrica" scope="col">
                                            <xsl:value-of select="metric"/>
                                        </th>
                                    </xsl:for-each>
                                </tr>
                                <tr>
                                    <th id="coverage" scope="row">
                                        <xsl:value-of select="coveragename"/>
                                    </th>
                                    <xsl:for-each select="surroundings/surroundingtype/riskfactors/riskfactor">
                                        <td>
                                            <a href="#{../../../../coveragename} {metric} {../../sname}">
                                                <xsl:value-of select="classification"/>
                                            </a>
                                        </td>
                                    </xsl:for-each>
                                </tr>
                            </xsl:for-each>
                        </table>
                        
                        <xsl:for-each select="classificationtable/coverageclass/surroundings/surroundingtype/riskfactors/riskfactor">
                            <div id="{../../../../coveragename} {metric} {../../sname}">
                                <h5>Relevant Surroundings&#160;<xsl:value-of select="../../../../coveragename"/>-<xsl:value-of select="metric"/>&#160;
                                    <xsl:value-of select="../../sname"/>
                                </h5>
                                <ul>
                                    <xsl:for-each select="relevantsurroundings/slocation">
                                        <li id="locations">
                                            <xsl:value-of select="."/>
                                        </li>
                                    </xsl:for-each>
                                </ul> 
                            </div>
                        </xsl:for-each>
                    
                        <hr/>
                    </div>
                </xsl:for-each>
                <div id="comment">
                    <h3>
                        Comments / Justification
                    </h3>
                    <p>
                        <xsl:value-of select="case/comment"/>
                    </p>
                </div>
                <div id="history">
                    <h3>
                        History
                    </h3>
                    <ul>
                        <xsl:for-each select="case/history/entry">
                            <li id="entry">
                                <xsl:value-of select="."/>
                            </li>
                        </xsl:for-each>
                    </ul> 
                        
                </div>

            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
