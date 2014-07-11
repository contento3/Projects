package com.contento3.module.email.provider;

import com.contento3.module.email.provider.impl.EmailProviderContext;

public interface EmailProvider {

	Boolean send(EmailProviderContext emailContext);
}
