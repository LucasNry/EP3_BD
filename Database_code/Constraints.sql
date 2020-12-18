-- Esse arquivo trás como cada uma das solicitações foram implementadas em SQL

-- b.	Qualquer chefe militar obedece no mínimo a um líder político.
-- resolvida tornando obrigatorio colocar o nome do lider na hora da criação do chefe militar 

-- c.	Uma divisão é dirigida pelo menos por um chefe militar.
CREATE FUNCTION _divisao_comando_min1() RETURNS trigger AS $_divisao_comando_min1$
    BEGIN
    	IF ((SELECT COUNT(codigo_chef) FROM chefemilitar WHERE nrodivisao = NEW.nrodivisao) = 0)
			THEN RAISE EXCEPTION 'Essa divisão ainda não possui um chefe, indique um chefe antes de criar a divisão';

	END IF;
	RETURN NEW;
    END;
	
$_divisao_comando_min1$ LANGUAGE plpgsql;

CREATE TRIGGER _divisao_comando_min1 BEFORE INSERT OR UPDATE ON divisao
FOR EACH ROW EXECUTE FUNCTION _divisao_comando_min1();


-- d.	Uma divisão é dirigida pelo menos por um chefe militar.
CREATE FUNCTION _divisao_comando_() RETURNS trigger AS $_divisao_comando_$
    BEGIN
	IF ((SELECT COUNT(codigo_chef) FROM chefemilitar WHERE nrodivisao = NEW.nrodivisao) = 3)
			THEN RAISE EXCEPTION 'Essa divisão já possui chefes demais';
	END IF;
	RETURN NEW;
    END;
	
$_divisao_comando_$ LANGUAGE plpgsql;

CREATE TRIGGER _divisao_comando_ BEFORE INSERT OR UPDATE ON chefemilitar
FOR EACH ROW EXECUTE FUNCTION _divisao_comando_();

--e.	Todo grupo armado dispõe de no mínimo uma divisão
CREATE FUNCTION _grupoarmado_divisao_min1() RETURNS trigger AS $_grupoarmado_divisao_min1$
    BEGIN
    	IF ((SELECT COUNT(nrodivisao) FROM divisao WHERE codigog = NEW.codigog) = 0)
			THEN RAISE EXCEPTION 'Esse grupo armado ainda não uma divisao, abra uma divisão antes de registrar o grupo armado';

	END IF;
	RETURN NEW;
    END;
	
$_grupoarmado_divisao_min1$ LANGUAGE plpgsql;

CREATE TRIGGER _grupoarmado_divisao_min1 BEFORE INSERT OR UPDATE ON grupoarmado
FOR EACH ROW EXECUTE FUNCTION _grupoarmado_divisao_min1();

-- f.	Em um conflito armado participam como mínimo dois grupos armados.
CREATE FUNCTION _conflito_2_paises_at_least_() RETURNS trigger AS $_conflito_2_paises_at_least_$
    BEGIN
    	IF (COALESCE((SELECT COUNT(codigog) FROM entpart WHERE codconflito = NEW.codconflito),0) < 2)
			THEN RAISE EXCEPTION 'Esse conflito não possui dois paises envolvidos, adicione os paises que participam do conflito primeiro';
		END IF;
	RETURN NEW;
    END;
	
$_conflito_2_paises_at_least_$ LANGUAGE plpgsql;

CREATE TRIGGER _conflito_2_paises_at_least_ BEFORE INSERT ON conflito
FOR EACH ROW EXECUTE FUNCTION _conflito_2_paises_at_least_();


--g.	Qualquer conflito afeta pelo menos um país.
CREATE FUNCTION _conflito_pais_() RETURNS trigger AS $_conflito_pais_$
    BEGIN
	IF ((SELECT COUNT(pais) FROM conflitopais WHERE codconflito = NEW.codconflito) = 0)
			THEN RAISE EXCEPTION 'Esse pai precisa ser adicionado a tabela conflitopais antes';
	END IF;
	RETURN NEW;
    END;
	
$_conflito_pais_$ LANGUAGE plpgsql;

CREATE TRIGGER _conflito_pais_ BEFORE INSERT OR UPDATE ON conflito
FOR EACH ROW EXECUTE FUNCTION _conflito_pais_();

-- h.	Com um disparador (trigger), procedimento armazenado o dentro do código dos programas você deveria: Manter a consistência das baixas totais em cada grupo armado, a partir das baixas produzidas nas suas divisões; gerar e assegurar a sequencialidade do número de divisão dentro do grupo armado.
CREATE FUNCTION _baixa_gp_() RETURNS trigger AS $_baixa_gp_$
    BEGIN
	UPDATE public.grupoarmado SET numbaixasg = (SELECT 
												CASE WHEN (SELECT SUM(numbaixasd) FROM divisao WHERE codigog = NEW.codigog AND nrodivisao <> NEW.nrodivisao) IS NULL THEN 0 
												ELSE (SELECT SUM(numbaixasd) FROM divisao WHERE codigog = NEW.codigog AND nrodivisao <> NEW.nrodivisao) END + NEW.numbaixasd WHERE codigog = NEW.codigog); 
	RETURN NEW;
    END;
	
$_baixa_gp_$ LANGUAGE plpgsql;

CREATE TRIGGER _baixa_gp_ BEFORE UPDATE ON divisao
FOR EACH ROW EXECUTE FUNCTION _baixa_gp_();

CREATE FUNCTION _baixa_gp_insert() RETURNS trigger AS $_baixa_gp_insert$
    BEGIN
	UPDATE public.grupoarmado SET numbaixasg = (SELECT numbaixasg FROM grupoarmado WHERE codigog = NEW.codigog) + NEW.numbaixasd;
	RETURN NEW;
    END;
	
$_baixa_gp_insert$ LANGUAGE plpgsql;

CREATE TRIGGER _baixa_gp_insert BEFORE INSERT ON divisao
FOR EACH ROW EXECUTE FUNCTION _baixa_gp_insert();
