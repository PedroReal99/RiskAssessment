
<!--<?xml version="1.0" encoding="UTF-8"?>-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>
    <!-- template SE04 -->
    <xsl:template match="/SE04">
        <html>
            <head>
                <title>Comparacao de analise de risco entre 2 matrizes</title>
            </head>
<body>Result: <xsl:value-of select="Result"/>
            </body>

        </html>
    </xsl:template>
    
    
</xsl:stylesheet>