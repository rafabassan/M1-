UPDATE product SET distribution_center = 'MOGI_DAS_CRUZES' WHERE id IN ('p1','p4','p7');
UPDATE product SET distribution_center = 'RECIFE' WHERE id IN ('p2','p5','p8');
UPDATE product SET distribution_center = 'PORTO_ALEGRE' WHERE id IN ('p3','p6');
ALTER TABLE product ALTER COLUMN distribution_center SET NOT NULL;
